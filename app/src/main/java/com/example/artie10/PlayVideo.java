package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class PlayVideo extends AppCompatActivity {

    private VideoView videoView;
    private ImageView playButton;
    private ImageView pauseButton;
    private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar videoProgress;
    private Uri videoUri;
    public boolean isPlaying;

    private int current;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        videoView = (VideoView) findViewById(R.id.videoView);

        playButton = (ImageView) findViewById(R.id.playButton);
        playButton.setImageResource(R.drawable.pause_button);

        currentTimer = (TextView) findViewById(R.id.currentTimer);
        durationTimer = (TextView) findViewById(R.id.durationTimer);

        videoProgress = (ProgressBar) findViewById(R.id.videoProgressBar);
        videoProgress.setMax(100);

        isPlaying = true;
        current = 0;
        duration = 0;

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration = mp.getDuration() / 1000;
                String durationString = String.format( "%02d:%02d", duration / 60, duration % 60);
                durationTimer.setText(durationString);
            }
        });

        new VideoProgress().execute();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    videoView.pause();
                    isPlaying = false;
                    playButton.setImageResource(R.drawable.play_button);
                }
                else{
                    videoView.start();
                    isPlaying = true;
                    playButton.setImageResource(R.drawable.pause_button);
                }
            }
        });

        playVideo();

    }

    public void playVideo(){
        //this part was necessary to transfer some info from RetrieveVideo class
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String transferInfo = bundle.getString("transferInfo");

        //Toast.makeText(PlayVideo.this, transferInfo + "f", Toast.LENGTH_SHORT).show();

        //transferInfo is download URL of the video in string form
        if (transferInfo != null) {
            videoUri = Uri.parse(transferInfo);

            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.start();
        }
    }

    protected void onStop(){
        super.onStop();

        isPlaying = false;
    }

    public class VideoProgress extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            do{
                if(isPlaying) {

                    current = videoView.getCurrentPosition() / 1000;
                    publishProgress(current);
                }

            } while(videoProgress.getProgress() <= 100 );

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            try{
                int currentPercent = values[0] * 100 / duration;
                videoProgress.setProgress(currentPercent);

                String currentString = String.format("%02d:%02d", values[0] / 60, values[0] % 60);

                currentTimer.setText(currentString);

            } catch(Exception e){
            }
        }
    }
}
