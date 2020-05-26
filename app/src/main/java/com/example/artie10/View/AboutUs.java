package com.example.artie10.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.artie10.R;


public class AboutUs extends AppCompatActivity {

    //properties
    Toolbar appbar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_about_us);

        appbar = findViewById( R.id.appbar);
        setSupportActionBar( appbar);
        getSupportActionBar().setTitle( "About Us");
    }
}
