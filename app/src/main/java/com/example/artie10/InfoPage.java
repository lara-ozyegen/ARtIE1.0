package com.example.artie10;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 30/04/2020
 * This class shows information and preview about selected models
 */

public class InfoPage extends AppCompatActivity {

    //variables
    private String text;
    private TextView textView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_preview);

        //Making it a pop-up
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm);

        int width = dm. widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( ( int ) ( width * .9 ), ( int ) ( height * .6 ));

        //Getting the text value from ARModels class which gets it from realtime database
        Intent i = getIntent();
        text = i.getStringExtra("PreviewOfModel");

        textView = findViewById( R.id.infoOfModel);
        textView.setText( text);
    }
}
