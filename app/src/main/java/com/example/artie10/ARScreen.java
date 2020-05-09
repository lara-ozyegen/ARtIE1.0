package com.example.artie10;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.artie10.Model.ARModels;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ARScreen extends AppCompatActivity {

    //properties
    private ArFragment arFragment;
    private ImageView pencil;
    private VideoView videoView;
    private ToggleButton toggleButton;
    private String videoURI = "";
    private ImageButton infoButton;
    private TextView sessionID;
    private String text;
    private RelativeLayout relativeLayout2;

    private ARModels models;

    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private MediaProjectionCallBack mediaProjectionCallBack;
    private MediaRecorder mediaRecorder;

    private VirtualDisplay virtualDisplay;
    private int mScreenDensity;
    private static  int DISPLAY_WIDTH = 720;
    private static  int DISPLAY_HEIGHT = 1280;

    static {
        ORIENTATIONS.append( Surface.ROTATION_0,0 );
        ORIENTATIONS.append( Surface.ROTATION_90,90 );
        ORIENTATIONS.append( Surface.ROTATION_180,180 );
        ORIENTATIONS.append( Surface.ROTATION_270,270 );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_a_r_screen );

        arFragment = ( ArFragment ) getSupportFragmentManager().findFragmentById( R.id.arFragment );

        //getting the text of the button from the previous activity
        Intent i = getIntent();
        text = i.getStringExtra("ButtonText");
        models = new ARModels(this, arFragment,text);

        //Initializing firebase and downloading model from firebase
        models.DownloadModel();

        //inserting the model
        arFragment.setOnTapArPlaneListener( ( hitResult, plane, motionEvent ) -> {
            models.InsertModel(hitResult);
        });

        ImageView pencil = findViewById( R.id.pencil );
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaint();
            }
        });

        //info and session id buttons, they are not visible because this is the code of free mode
        infoButton = ( ImageButton ) findViewById( R.id.infoButton );
        infoButtonSettings( false, false );

        sessionID = (TextView) findViewById(R.id.session_id);
        sessionID.setText( " Session ID: 1234");

        ActivityCompat.requestPermissions(this, new String[] {
            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_GRANTED );

        //otherwise app crashes
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy( builder.build() );

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( metrics );
        mScreenDensity = metrics.densityDpi;

        DISPLAY_HEIGHT = metrics.heightPixels;
        DISPLAY_WIDTH = metrics.widthPixels;

        mediaRecorder = new MediaRecorder();
        mediaProjectionManager = ( MediaProjectionManager )getSystemService( Context.MEDIA_PROJECTION_SERVICE );

        //view
        videoView = ( VideoView )findViewById( R.id.videoView );
        toggleButton = ( ToggleButton )findViewById( R.id.toggleButton );
        //relativeLayout2 = (RelativeLayout)findViewById(R.id.relativeLayout2);

        //video
        toggleButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( ContextCompat.checkSelfPermission(ARScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                        + ContextCompat.checkSelfPermission( ARScreen.this, Manifest.permission.RECORD_AUDIO )
                != PackageManager.PERMISSION_GRANTED )
                {
                    if( ActivityCompat.shouldShowRequestPermissionRationale(ARScreen.this, WRITE_EXTERNAL_STORAGE ) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(ARScreen.this, Manifest.permission.RECORD_AUDIO ) )
                    {
                        toggleButton.setChecked( false );
                        Snackbar.make( relativeLayout2, "Permissions", Snackbar.LENGTH_INDEFINITE )
                                .setAction( "ENABLE", new View.OnClickListener() {
                       @Override
                       public void onClick( View v ) {
                        ActivityCompat.requestPermissions(ARScreen.this,
                                new String[]{
                                        WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO },
                                REQUEST_PERMISSION );
                       }

                    }).show();

                     }
                         else{
                                 ActivityCompat.requestPermissions(ARScreen.this,
                                    new String[]{
                                            WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO },
                                    REQUEST_PERMISSION );
                            }
                }
                else{
                    toggleScreenShare( v );
                }
             }

        });
    }//end of onCreate


    /**
     *
     * @param view
     */
    public void ScreenshotButton( View view ) {
        //take screenshot

        //View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        ////View screenView = findViewById(R.id.relativeLayout2).getRootView();
        //View screenView = view.getRootView();
        ////screenView.setDrawingCacheEnabled(true);

        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled( true );

        int height = view1.getHeight();
        int width = view1.getWidth();

        view1.layout( 0,0,width,height );
        view1.buildDrawingCache( true );

        //create bitmap to draw the screenshot
        ////Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        ////screenView.setDrawingCacheEnabled(false);
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        Canvas canvas = new Canvas( bitmap );
        Drawable bgDrawable = view1.getBackground();
        if ( bgDrawable != null )
            bgDrawable.draw( canvas );
        else
            canvas.drawColor( Color.WHITE );
        view1  .draw( canvas );

        //create file
        String filePath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString() + ".jpg";
        File fileScreenshot = new File( filePath );

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream( fileScreenshot );
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, fileOutputStream );
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent( Intent.ACTION_VIEW );
        Uri uri = Uri.fromFile( fileScreenshot );
        intent.setDataAndType( uri,"image/.jpeg" );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        this.startActivity( intent );
    }

    /**
     *
     */
    public void openPaint(){
        Intent intent = new Intent( this,PaintActivity.class );
        startActivity( intent );
    }

    /**
     *
     */
    //Screen recording
    private class MediaProjectionCallBack extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if( toggleButton.isChecked() ){
                toggleButton.setChecked( false );
                mediaRecorder.stop();
                mediaRecorder.reset();
            }
            mediaProjection = null;
            stopRecordScreen();
            super.onStop();
        }
    }

    /**
     *
     */
    private void stopRecordScreen() {
        if( virtualDisplay == null )
            return;

        virtualDisplay.release();
        destroyMediaProjection();
    }

    /**
     *
     */
    private void destroyMediaProjection() {
        if( mediaProjection != null ){
            mediaProjection.unregisterCallback( mediaProjectionCallBack );
            mediaProjection.stop();
            mediaProjection = null;
        }

    }

    /**
     *
     * @param v
     */
    private void toggleScreenShare( View v ){
        if( ( (ToggleButton) v ).isChecked() )
        {
            initRecorder();
            recordScreen();
        }
        else{
            mediaRecorder.stop();
            mediaRecorder.reset();
            stopRecordScreen();

            //videoView.setVisibility( View.VISIBLE );
           // videoView.setVideoURI( Uri.parse(videoURI ) );
           // videoView.start();
            Intent intent = new Intent (ARScreen.this, UploadVideo.class );
            startActivity( intent );

        }

    }

    /**
     *
     */
    private void initRecorder(){
        try{
            mediaRecorder.setAudioSource( MediaRecorder.AudioSource.MIC );
            mediaRecorder.setVideoSource( MediaRecorder.VideoSource.SURFACE );
            mediaRecorder.setOutputFormat( MediaRecorder.OutputFormat.THREE_GPP );

            videoURI = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS )
                    + new StringBuilder( "/EDMTRecord_" ).append( new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss" )
            .format(new Date())).append( " .mp4" ).toString();

            mediaRecorder.setOutputFile( videoURI );
            mediaRecorder.setVideoSize( DISPLAY_WIDTH, DISPLAY_HEIGHT );
            mediaRecorder.setVideoEncoder( MediaRecorder.VideoEncoder.H264 );
            mediaRecorder.setAudioEncoder( MediaRecorder.AudioEncoder.AMR_NB );
            mediaRecorder.setVideoEncodingBitRate( 512 * 1000 );
            mediaRecorder.setVideoFrameRate( 30 );

            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATIONS.get( rotation * 90);
            mediaRecorder.setOrientationHint( orientation );
            mediaRecorder.prepare();

        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    private void recordScreen(){

        if( mediaProjection == null ){

            startActivityForResult( mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE );
            return;
        }
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();

    }

    /**
     *
     * @return
     */
    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay( "ARScreen", DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
               DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
              mediaRecorder.getSurface(), null, null );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if( requestCode!= REQUEST_CODE ){
            Toast.makeText( this, "Unk error", Toast.LENGTH_SHORT ).show();
            return;
        }

        if( resultCode != RESULT_OK ){
            Toast.makeText( this, "Permission denied", Toast.LENGTH_SHORT ).show();
            toggleButton.setChecked( false );
            return;
        }

        mediaProjectionCallBack = new MediaProjectionCallBack();
        mediaProjection = mediaProjectionManager.getMediaProjection( resultCode, data );
        mediaProjection.registerCallback( mediaProjectionCallBack, null );
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch ( requestCode ){
            case REQUEST_PERMISSION:
            {
                if( ( grantResults.length > 0) && (grantResults[0] + grantResults[1] == PERMISSION_GRANTED ) ){
                    toggleScreenShare( toggleButton );
                }
                else{
                    toggleButton.setChecked( false );
                    Snackbar.make( relativeLayout2, "Permissions", Snackbar.LENGTH_INDEFINITE )
                            .setAction( "ENABLE", new View.OnClickListener() {
                                @Override
                                public void onClick( View v ) {
                                    ActivityCompat.requestPermissions(ARScreen.this,
                                            new String[]{
                                                    WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO },
                                            REQUEST_PERMISSION );
                                }

                            } ).show();
                }
                return;
            }
        }
    }

    /**
     * a method to change the visibility and enabled/disabled status of infoButton.
     * @param enableDisable
     * @param visibility
     */
    public void infoButtonSettings( boolean enableDisable, boolean visibility ){

        //setting the button as enabled or disabled
        infoButton.setEnabled( enableDisable );

        //setting the button as visible or invisible
        if( visibility == true ) {
            infoButton.setVisibility(View.VISIBLE );
            sessionID.setVisibility(View.VISIBLE);

        }
        else{
            infoButton.setVisibility( View.GONE );
        }
    }

    public String returnVideoURI(  ){
        return videoURI;
    }


}
