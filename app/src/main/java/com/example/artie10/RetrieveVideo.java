package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class RetrieveVideo extends AppCompatActivity {
    private Button watchVideo;
    private EditText videoName;
    private StorageReference videoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_video);

        watchVideo = (Button) findViewById(R.id.watchVideo);
        videoName = (EditText) findViewById(R.id.videoName);

        watchVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v ) {
                try {
                    downloadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadFile() throws IOException {

        File localFile = File.createTempFile(  "videos", "mp4");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        videoRef = storage.getReference().child( "videos/" + videoName.getText().toString());

        videoRef.getFile( localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess( FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RetrieveVideo.this, "yasssss", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(RetrieveVideo.this, "offfff", Toast.LENGTH_SHORT).show();
            }
        });

        //after retrieving  the video, navigate the user to UploadVideo page
        Intent intent = new Intent (RetrieveVideo.this, PlayVideo.class );
        //creating a bundle to transfer information to PlayVideo
        Bundle bundle= new Bundle();
        //to play a video from firebase, we need the reference to storage in string form
        bundle.putString( "transferInfo", videoRef.getDownloadUrl() + "" );
        //inserting the bundle into intent to be sent to PlayVideo
        intent.putExtras( bundle );
        startActivity( intent );
    }

        /*
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("<https://console.firebase.google.com/u/0/project/artie-638c3/storage/artie-638c3.appspot.com/files~2Fvideos>");
        StorageReference islandRef = storageRef.child( videoName.getText().toString() + ".txt");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "file_name");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,"imageName.txt");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";local tem file created  created " +localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });

         */
    }



