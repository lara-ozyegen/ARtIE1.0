package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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
        ImageView addModel = (ImageView) findViewById(R.id.addModel);
        addModel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //opens downloads page --- how ?
            }
        });
/**
        ImageView myModels = (ImageView) findViewById(R.id.myModels);
        myModels.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMyModels();
            }
        });

        ImageView maths = (ImageView) findViewById(R.id.maths);
        maths.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMaths();
            }
        });

        ImageView chem = (ImageView) findViewById(R.id.chemistry);
        chem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openChemistry();
            }
        });

        ImageView bio = (ImageView) findViewById(R.id.bio);
        bio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBiology();
            }
        });

        ImageView space = (ImageView) findViewById(R.id.space);
        space.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSpace();
            }
        });

        public void openBiology(){

        }
        public void openMaths(){

        }
        public void openMyModels(){

        }
        public void openSpace(){

        }
        public void openChemistry(){

        }*/
    }
}
