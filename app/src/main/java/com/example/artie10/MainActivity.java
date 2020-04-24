package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.artie10.ui.login.JoinPopup;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.freeSession);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
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
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT).show();
                openCategories();
                return true;
            case R.id.joinSession:
                openLogin();
                //Toast.makeText(this, "Item 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    public void openActivity2(){
        Intent intent = new Intent(this, SecondScreen.class);
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
