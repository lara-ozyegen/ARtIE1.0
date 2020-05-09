package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class UploadVideo extends AppCompatActivity {

    //properties
    private Button yes;
    private Button no;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_video );

        makeItPopUp();

        yes = (Button) findViewById( R.id.yesButton );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVideo();
                //close popup screen
                finish();
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
    }

    public void makeItPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = ( int ) ( dm.widthPixels * .6 );
        int height = ( int ) ( dm.heightPixels * .6 );

        getWindow().setLayout( width, height );
    }

    public void uploadVideo(){
    }
}
