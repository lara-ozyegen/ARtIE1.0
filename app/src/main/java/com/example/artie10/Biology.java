package com.example.artie10;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 15/04/2020
 * This class shows models of biology
 */

public class Biology extends AppCompatActivity {

    //constant
    private static final int REQUEST_CODE = 45;

    //variables
    Toolbar appbar;
    public ArrayAdapter<String> arrayAdapter;
    private List<String> myList;
    private ListView listView;
    private Button brainButton;
    private Button covidButton;
    private Button heartButton;
    private Button skullButton;
    private Button antibodyButton;
    private Button lungsButton;
    private String text;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_biology);

        brainButton = ( Button ) findViewById( R.id.brain_button);
        brainButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v ) {
                text = brainButton.getText().toString().toLowerCase();
                openAR();
            }
        } );

        heartButton = (Button) findViewById(R.id.heart_button);
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = heartButton.getText().toString().toLowerCase();
                openAR();
            }
        });

        skullButton = (Button) findViewById(R.id.skullButton);
        skullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = skullButton.getText().toString().toLowerCase();
                openAR();
            }
        });

        antibodyButton = ( Button) findViewById( R.id.antibodyButton);
        antibodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = antibodyButton.getText().toString().toLowerCase();
                openAR();
            }
        });

        lungsButton = ( Button) findViewById( R.id.lungsButton);
        lungsButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v ) {
                text = lungsButton.getText().toString().toLowerCase();
                openAR();
            }
        } );

        covidButton = (Button) findViewById( R.id.covidButton);
        covidButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v ) {
                text = covidButton.getText().toString().toLowerCase();
                openAR();
            }
        } );

        appbar = findViewById( R.id.appbar);
        setSupportActionBar( appbar);
        getSupportActionBar().setTitle( "Biology");

        listView = findViewById( R.id.listView);

        myList = new ArrayList<>();
        myList.clear();
        //Adapter has to use string so we use text contents of the buttons
        myList.add(( String) brainButton.getText());
    }

    /**
     * This method opens AR screen
     */
    public void openAR() {
        if( MainActivity.getPath()){
            Intent intent = new Intent( this , ARScreenSession.class);
            intent.putExtra("TextOfButton", text);
            startActivity( intent);
        }else{
            Intent intent = new Intent( this , ARScreen.class);
            intent.putExtra("TextOfButton", text);
            startActivity( intent);
        }
    }

    /**
     * This method opens help screen
     */
    public void openHelp(){
        Intent intent = new Intent( this, HelpScreen.class);
        startActivity( intent);
    }

    /**
     * This method returns home page
     */
    public void openHome(){
        Intent intent = new Intent( this, MainActivity.class);
        startActivity( intent);
    }

    /**
     * This method opens search screen
     */
    public void openSearch(){
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myList );
        listView.setAdapter( arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu);

        MenuItem menuItem = menu.findItem( R.id.search);
        SearchView searchView = ( SearchView ) menuItem.getActionView();
        searchView.setQueryHint( "Search in biology");
        searchView.setImeOptions( EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit( String query ) {
                return false;
            }

            @Override
            public boolean onQueryTextChange( String newText ) {
                arrayAdapter.getFilter().filter( newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu( menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch ( item.getItemId() ) {
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
                return super.onOptionsItemSelected( item);
        }
    }
}
