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

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 15/05/2020
 * This class makes uploading video possible
 */
public class UploadVideo extends AppCompatActivity {

    //properties
    private Button yes;
    private Button no;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private File videoFile;
    private Uri filePath;
    private TextView addVideoName;
    private EditText inputVideoName;
    private Button uploadButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_video );

        //so upload video will be a pop-up window
        makeItPopUp();

        //initializing properties
        addVideoName = (TextView) findViewById(R.id.addVideoName);
        inputVideoName = (EditText) findViewById(R.id.inputVideoName);
        uploadButton = (Button) findViewById(R.id.uploadButton);

        yes = (Button) findViewById( R.id.yesButton );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //changing visibility and enabled status of some properties
                //because yes is clicked, and they are needed for the next step
                addVideoName.setVisibility(View.VISIBLE);
                inputVideoName.setVisibility(View.VISIBLE);
                uploadButton.setVisibility(View.VISIBLE);
                inputVideoName.setEnabled(true);
                uploadButton.setEnabled(true);

                uploadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadVideo();
                        //close popup screen
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

        //this part was necessary to transfer some info from ARScreen class
        Bundle bundle = getIntent().getExtras();
        String transferInfo = bundle.getString("transferInfo");

        //getting a storage and its reference from firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //getting the video info and creating the uri of the file to be uploaded
        videoFile = new File( transferInfo );
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

    /**
     * a method which uploads the recorded video to firebase
     */
    public void uploadVideo(){
        if( filePath != null )
        {
            final ProgressDialog progressDialog = new ProgressDialog(this );
            progressDialog.setTitle( "Uploading..." );
            progressDialog.show();

            //this reference is where the video will be put in the firebase storage
            //the name of the video is taken from the user
            StorageReference ref = storageReference.child( "videos/" + inputVideoName.getText().toString() );
            ref.putFile( filePath )
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot ) {
                            //progressDialog.dismiss();
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
