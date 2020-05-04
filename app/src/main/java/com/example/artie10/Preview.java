package com.example.artie10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Preview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm. widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.8));

        Button b = (Button) findViewById( R.id.ar_button);
        b.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                if ( MainActivity.getPath())
                    //openAR();
                    openSessionAR();
                else
                    openSessionAR();
            }
        });


    }
    public void openAR(){
        Intent intent = new Intent(this, ARScreen.class);
        startActivity( intent);
    }

    public void openSessionAR() {
        Intent intent = new Intent(this, ARScreenSession.class);
        startActivity( intent);
    }


}
