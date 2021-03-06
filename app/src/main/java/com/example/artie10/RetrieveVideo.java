package com.example.artie10;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private String url;
    private File localFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_video);

        watchVideo = ( Button) findViewById( R.id.watchVideo);
        videoName = ( EditText) findViewById( R.id.videoName);

        watchVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    downloadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadFile() throws IOException {

        File localFile = File.createTempFile("videos", "mp4");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        videoRef = storage.getReference().child( "videos/" + videoName.getText().toString());

        videoRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText( RetrieveVideo.this, "Video found and playing", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(exception -> Toast.makeText( RetrieveVideo.this, "Video Not Found", Toast.LENGTH_SHORT).show());


        //to play a video from firebase, we need the reference to storage in string form

        videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString(); // Got the download URL for 'users/me/profile.png'

                //after retrieving  the video, navigate the user to UploadVideo page
                Intent intent = new Intent(RetrieveVideo.this, PlayVideo.class);
                //creating a bundle to transfer information to PlayVideo
                Bundle bundle = new Bundle();

                bundle.putString( "transferInfo", url);


                //inserting the bundle into intent to be sent to PlayVideo
                intent.putExtras( bundle );
                startActivity( intent );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }
}