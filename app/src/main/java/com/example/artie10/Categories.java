package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories3);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#BE40FF")));

        //Adding onClickListeners for all of the categories
        ImageView bio = (ImageView) findViewById(R.id.bio);
        bio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBiology();
            }
        });
    }
    public void openBiology(){
        Intent intent = new Intent(this, Biology.class);
        startActivity(intent);
    }
}
