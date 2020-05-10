package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadVideo extends AppCompatActivity {

    //properties
    private Button yes;
    private Button no;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String videoString;
    private File videoFile;
    private Uri filePath;
    private TextView addVideoName;
    private EditText inputVideoName;
    private Button uploadButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_video );

        makeItPopUp();
        addVideoName = (TextView) findViewById(R.id.addVideoName);
        inputVideoName = (EditText) findViewById(R.id.inputVideoName);
        uploadButton = (Button) findViewById(R.id.uploadButton);

        yes = (Button) findViewById( R.id.yesButton );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //close popup screen
                addVideoName.setVisibility(View.VISIBLE);
                inputVideoName.setVisibility(View.VISIBLE);
                uploadButton.setVisibility(View.VISIBLE);
                inputVideoName.setEnabled(true);
                uploadButton.setEnabled(true);

                uploadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadVideo();
                        finish();
                    }
                });
            }
        });

        no = (Button) findViewById( R.id.noButton );
        no.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close popup screen
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("stuff");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        videoFile = new File( stuff );
        filePath = Uri.fromFile( videoFile );

    }

    /**
     * a method which makes the related window a pop-up.
     */
    public void makeItPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = ( int ) ( dm.widthPixels * .8 );
        int height = ( int ) ( dm.heightPixels * .8 );

        getWindow().setLayout( width, height );
    }

    public void uploadVideo(){
        if( filePath != null )
        {
            final ProgressDialog progressDialog = new ProgressDialog(this );
            progressDialog.setTitle( "Uploading..." );
            progressDialog.show();

            StorageReference ref = storageReference.child( "videos/" + inputVideoName.getText().toString() );
            ref.putFile( filePath )
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot ) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadVideo.this, "Uploaded", Toast.LENGTH_SHORT ).show();
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadVideo.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    })
                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress( UploadTask.TaskSnapshot taskSnapshot ) {
                            double progress = ( 100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount() );
                            progressDialog.setMessage( "Uploaded " + ( int ) progress + "%" );
                        }
                    });
        }
    }


}
