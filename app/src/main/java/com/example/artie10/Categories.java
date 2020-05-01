package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Categories extends AppCompatActivity {
    private Button bio;
    private Button add;
    private static final int REQUEST_CODE = 45;
    Toolbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories3);

        //getSupportActionBar().setBackgroundDrawable(
               // new ColorDrawable(Color.parseColor("#BE40FF")));

        //Adding onClickListeners for all of the categories
        bio = (Button) findViewById(R.id.biologyButton);
        bio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBiology();
            }
        });

        add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addModel();
            }
        });
        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);
    }
    public void openBiology(){
        Intent intent = new Intent(Categories.this, Biology.class);
        startActivity(intent);
    }


    private void addModel(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if( data != null){
                Uri uri = data.getData();
                Toast.makeText( this, "Uri :" +uri, Toast.LENGTH_LONG ).show();
                //Toast.makeText( this, "Path :" + uri.getPath(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void openHelp(){
        Intent intent = new Intent(this, HelpScreen.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.help2:
                openHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
