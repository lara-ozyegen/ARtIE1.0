package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayVideo extends AppCompatActivity {

    private VideoView videoView;
    private ImageView play;
    private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar videoProgress;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        videoView = (VideoView) findViewById(R.id.videoView);
        play = (ImageView) findViewById(R.id.playVideo);
        currentTimer = (TextView) findViewById(R.id.currentTimer);
        durationTimer = (TextView) findViewById(R.id.durationTimer);
        videoProgress = (ProgressBar) findViewById(R.id.videoProgressBar);

        playVideo();
    }

    public void playVideo(){
        //this part was necessary to transfer some info from RetrieveVideo class
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String transferInfo = bundle.getString("transferInfo");

        Toast.makeText(PlayVideo.this, transferInfo + "f", Toast.LENGTH_SHORT).show();

        //transferInfo is download URL of the video in string form
        if( transferInfo != null) {
            videoUri = Uri.parse( transferInfo);

            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.start();

            //Toast.makeText(PlayVideo.this, "lalaallllala", Toast.LENGTH_SHORT).show();
        }
    }
}
