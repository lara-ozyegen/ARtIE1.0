package com.example.artie10;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ARScreen extends AppCompatActivity {

    private ArFragment arFragment;
    private ImageView pencil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_screen);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("1410 Heart.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(throwable.getMessage())
                                .show();
                        return null;
                    });
        });

        ImageView pencil = findViewById(R.id.pencil);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaint();
            }
        });


        ActivityCompat.requestPermissions(this, new String[] {
            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_GRANTED);

        //otherwise app crashes
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }//end of onCreate

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }


    public void ScreenshotButton( View view) {
        //take screenshot

        //View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        ////View screenView = findViewById(R.id.relativeLayout2).getRootView();
        //View screenView = view.getRootView();
        ////screenView.setDrawingCacheEnabled(true);

        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled(true);

        int height = view1.getHeight();
        int width = view1.getWidth();
        view1.layout(0,0,width,height);
        view1.buildDrawingCache(true);

        //create bitmap to draw the screenshot
        ////Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        ////screenView.setDrawingCacheEnabled(false);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        view1.buildDrawingCache(false);
        Canvas canvas = new Canvas( bitmap);
        Drawable bgDrawable = view1.getBackground();
        //if (bgDrawable != null)
            bgDrawable.draw(canvas);
        //else
           // canvas.drawColor(Color.TRANSPARENT);
        view1.draw( canvas);
        view1.draw( canvas);

        //create file
        String filePath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString() + ".jpg";
        File fileScreenshot = new File(filePath);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(fileScreenshot);
        intent.setDataAndType(uri,"image/.jpeg");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    public void openPaint(){
        Intent intent = new Intent(this,PaintActivity.class);
        startActivity(intent);
    }


}
