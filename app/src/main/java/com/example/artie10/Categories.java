package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {
    private Button bio;
    private Button add;
    private Button myModels;
    private Button math;
    private Button space;
    private Button chem;
    private static final int REQUEST_CODE = 45;
    Toolbar appbar;
    ArrayAdapter<String> arrayAdapter;
    List<String> myList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories3);

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

        math = (Button) findViewById(R.id.mathButton);
        space = (Button) findViewById(R.id.spaceButton);
        chem = (Button) findViewById(R.id.chemButton);
        myModels = (Button) findViewById(R.id.myModelsButton);

        //adding toolbar
        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        getSupportActionBar().setTitle("Categories");

        //adding a list for the use of search button in toolbar
        listView = findViewById(R.id.listView);
        myList = new ArrayList<>();
        myList.clear();
        //Adapter has to use string so we use text contents of the buttons
        myList.add((String) bio.getText()); myList.add((String) add.getText());
        myList.add((String) math.getText()); myList.add((String) space.getText());
        myList.add((String) chem.getText()); myList.add((String) myModels.getText());

        //Did the following code on a method so it would only open if we pressed the help
        //arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myList);
        //listView.setAdapter(arrayAdapter);
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

    public void openHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSearch(){
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search in categories");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.help2:
                openHelp();
                return true;
            case R.id.home:
                openHome();
                return true;
            case R.id.search:
                openSearch();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
