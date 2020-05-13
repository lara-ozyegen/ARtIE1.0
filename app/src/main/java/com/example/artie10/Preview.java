package com.example.artie10;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Preview extends AppCompatActivity {

    //variables
    private Button arButton;
    private String text;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_preview );

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = dm. widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( ( int ) ( width * .5 ), ( int ) ( height * .5 ) );


    }
}
