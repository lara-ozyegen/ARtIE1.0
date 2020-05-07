package com.example.artie10;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Biology extends AppCompatActivity {
    private static final int REQUEST_CODE = 45;

    Toolbar appbar;

    public ArrayAdapter<String> arrayAdapter;
    private List<String> myList;
    private ListView listView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_biology );
        Button b = ( Button ) findViewById( R.id.heart_button );
        b.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View v ) {
                openPreview();
            }
        } );

        appbar = findViewById( R.id.appbar );
        setSupportActionBar( appbar );
        getSupportActionBar().setTitle( "Biology" );

        listView = findViewById( R.id.listView );

        myList = new ArrayList<>();
        myList.clear();
        //Adapter has to use string so we use text contents of the buttons
        myList.add( ( String ) b.getText() );
    }

    public void openPreview() {
        Intent intent = new Intent( this , Preview.class  );
        startActivity( intent );
    }

    public void openHelp(){
        Intent intent = new Intent( this, HelpScreen.class );
        startActivity( intent );
    }

    public void openHome(){
        Intent intent = new Intent( this, MainActivity.class );
        startActivity( intent );
    }

    protected void onActivityResult( int requestCode, int resultCode, Intent data ){
        super.onActivityResult( requestCode, resultCode, data );
        if( requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            if( data != null ){
                Uri uri = data.getData();
                Toast.makeText( this, "Uri :" +uri, Toast.LENGTH_LONG ).show();
                //Toast.makeText( this, "Path :" + uri.getPath(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void openSearch(){
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myList );
        listView.setAdapter( arrayAdapter );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );

        MenuItem menuItem = menu.findItem( R.id.search );
        SearchView searchView = ( SearchView ) menuItem.getActionView();
        searchView.setQueryHint( "Search in biology" );
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit( String query ) {
                return false;
            }

            @Override
            public boolean onQueryTextChange( String newText ) {
                arrayAdapter.getFilter().filter( newText );
                return true;
            }
        });

        return super.onCreateOptionsMenu( menu );
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

            default:
                return super.onOptionsItemSelected( item );
        }
    }
}
