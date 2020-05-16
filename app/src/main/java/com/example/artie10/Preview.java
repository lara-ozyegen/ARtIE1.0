package com.example.artie10;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Preview extends AppCompatActivity {

    //variables
    private Button arButton;
    private String text;
    private TextView textView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_preview );

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = dm. widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( ( int ) ( width * .9 ), ( int ) ( height * .6 ) );

        Intent i = getIntent();
        text = i.getStringExtra("PreviewOfModel");

        textView = findViewById(R.id.infoOfModel);
        textView.setText(text);
    }
}
