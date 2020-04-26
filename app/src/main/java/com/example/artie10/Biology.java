package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Biology extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology);
        Button b = (Button) findViewById( R.id.heart_button);
        b.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v) {
                openPreview();
            }
        } );
    }
    public void openPreview(){
        Intent intent = new Intent(this , Preview.class );
    }
}
