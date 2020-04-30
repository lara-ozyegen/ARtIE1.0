package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.artie10.ui.login.JoinPopup;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private Button button;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton)findViewById(R.id.help);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
               openHelp();
            }
        });

        button = (Button)findViewById(R.id.freeSession);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategories();
            }

        });

    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newSession:
                openCategories();
                return true;
            case R.id.joinSession:
                openLogin();
                return true;
            default:
                return false;
        }
    }

    public void openHelp(){
        Intent intent = new Intent(this, HelpScreen.class);
        startActivity(intent);
    }
    public void openCategories(){
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }
    public void openLogin() {
        Intent intent = new Intent(this, JoinPopup.class);
        startActivity(intent);
    }


}
