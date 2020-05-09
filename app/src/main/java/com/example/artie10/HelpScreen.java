package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageButton;

public class HelpScreen extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_help_screen );

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = ( int ) ( dm.widthPixels * .6 );
        int height = ( int ) ( dm.heightPixels * .6 );

        getWindow().setLayout( width, height );

    }
}
