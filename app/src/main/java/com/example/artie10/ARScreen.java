package com.example.artie10;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.core.content.FileProvider;

import com.example.artie10.Model.ARModels;
import com.google.android.material.snackbar.Snackbar;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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

    private ARModels models;

    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private MediaProjectionCallBack mediaProjectionCallBack;
    private MediaRecorder mediaRecorder;
    private RelativeLayout relativeLayout2;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_screen);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        //getting the text of the button from the previous activity
        Intent i = getIntent();
        text = i.getStringExtra("TextOfButton");
        models = new ARModels(this, arFragment, text);

        //Initializing firebase and downloading model from firebase
        models.DownloadModel();

        //inserting the model
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            models.InsertModel(hitResult);
        });

        ImageView cam = findViewById(R.id.screenshot);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        ImageView pencil = findViewById(R.id.pencil);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaint();
            }
        });

        infoButton = (ImageButton) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreview();
            }
        });

        sessionID = (TextView) findViewById(R.id.session_id);
        sessionID.setText(" Session ID: 1234");

        ActivityCompat.requestPermissions(this, new String[]{
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_GRANTED);

        //otherwise app crashes
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        DISPLAY_HEIGHT = metrics.heightPixels;
        DISPLAY_WIDTH = metrics.widthPixels;

        mediaRecorder = new MediaRecorder();
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        //view
        videoView = (VideoView) findViewById(R.id.videoView);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        //video
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ARScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(ARScreen.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ARScreen.this, WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(ARScreen.this, Manifest.permission.RECORD_AUDIO)) {
                        toggleButton.setChecked(false);
                        Snackbar.make(relativeLayout2, "Permissions", Snackbar.LENGTH_INDEFINITE)
                                .setAction("ENABLE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(ARScreen.this,
                                                new String[]{
                                                        WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                                                REQUEST_PERMISSION);
                                    }

                                }).show();

                    } else {
                        ActivityCompat.requestPermissions(ARScreen.this,
                                new String[]{
                                        WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                                REQUEST_PERMISSION);
                    }
                } else {
                    toggleScreenShare(v);
                }
            }

        });
    }

    /**
     * a method which calls preview page for model. might need an update later on.
     */
    public void openPreview(){
        Intent intent = new Intent( this, Preview.class  );
        startActivity( intent );
    }

    /**
     * a method which calls paint class.
     */
    public void openPaint(){
        Intent intent = new Intent( this, PaintActivity.class );
        startActivity( intent );
    }


    //methods for video recording start here

    /**
     *
     */
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
            Bundle bundle= new Bundle();
            bundle.putString("stuff", videoURI);
            intent.putExtras(bundle);
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

    //methods for video recording ends here

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

    /**
     *
     * @return a file name in string form
     */
    private  String generateFilename() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        return Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
    }

    /**
     *
     */
    private void saveBitmapToDisk(Bitmap bitmap, String filename) throws IOException {

        File out = new File(filename);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(filename);
             ByteArrayOutputStream outputData = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
            outputData.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw new IOException("Failed to save bitmap to disk", ex);
        }
    }

    /**
     *
     */
    private void takePhoto() {


        final String filename = generateFilename();
        ArSceneView view = arFragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(ARScreen.this, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Photo saved", Snackbar.LENGTH_LONG);
                snackbar.setAction("Open in Photos", v -> {
                    File photoFile = new File(filename);

                    Uri photoURI = Uri.fromFile(photoFile);

                    Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                });
                snackbar.show();

            } else {
                Toast toast = Toast.makeText(ARScreen.this,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }


}

