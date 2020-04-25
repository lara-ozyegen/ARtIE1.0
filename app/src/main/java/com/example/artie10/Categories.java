package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.drm.DrmStore;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories3);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#BE40FF")));
    }
}
