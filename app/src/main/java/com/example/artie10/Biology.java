package com.example.artie10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class Biology extends Activity {
    Toolbar appbar;
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
        appbar = findViewById(R.id.appbar);
        //setActionBar(appbar);
    }
    public void openPreview() {
        Intent intent = new Intent(this , Preview.class );
        startActivity(intent);
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
