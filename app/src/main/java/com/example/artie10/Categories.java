package com.example.artie10;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
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
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 20/04/2020
 * This class shows categories of models
 */

public class Categories extends AppCompatActivity {

    //properties
    private static final int REQUEST_CODE = 45;

    //variables
    private Button bio;
    private Button add;
    private Button myModels;
    private Button math;
    private Button space;
    private Button chem;
    private Toolbar appbar;
    public ArrayAdapter<String> arrayAdapter;
    private List<String> myList;
    private ListView listView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_categories3 );

        //adding onClickListeners for all of the categories
        bio = ( Button ) findViewById( R.id.biologyButton );
        bio.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                openBiology();
            }
        });

        add = (Button) findViewById( R.id.addButton );
        add.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                addModel();
            }
        });

        math = ( Button ) findViewById( R.id.mathButton );
        space = ( Button ) findViewById( R.id.spaceButton );
        chem = ( Button ) findViewById( R.id.chemButton );
        myModels = ( Button ) findViewById( R.id.myModelsButton );

        //adding toolbar
        appbar = findViewById( R.id.appbar );
        setSupportActionBar( appbar );
        getSupportActionBar().setTitle( "Categories" );

        //adding a list for the use of search button in toolbar
        listView = findViewById( R.id.listView );

        myList = new ArrayList<>();
        myList.clear();
        //Adapter has to use string so we use text contents of the buttons
        myList.add( ( String ) bio.getText() ); myList.add( ( String ) add.getText() );
        myList.add( ( String ) math.getText() ); myList.add( ( String ) space.getText() );
        myList.add( ( String ) chem.getText() ); myList.add( ( String ) myModels.getText() );

    }
    /**
     * This method opens category of biology
     */
    public void openBiology(){
        Intent intent = new Intent(Categories.this, Biology.class );
        startActivity( intent );
    }

    /**
     * This method orients to downloads from phone
     */
    private void addModel(){
        Intent intent = new Intent( Intent.ACTION_OPEN_DOCUMENT );
        intent.setType( "*/*" );
        intent.addCategory( Intent.CATEGORY_OPENABLE );
        startActivityForResult( intent, REQUEST_CODE );
    }

    /**
     * @param requestCode,resultCode,data
     * This methods takes uri of selected file
     */
    protected void onActivityResult( int requestCode, int resultCode, Intent data ){
        super.onActivityResult( requestCode, resultCode, data );
        if( requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            if( data != null ){
                Uri uri = data.getData();
                ARScreen.text = getFileName(uri);
                ARScreen.isMyModel = true;

                //Toast.makeText( this, "Path :" + uri.getPath(), Toast.LENGTH_LONG).show();
                Toast.makeText( this, "Name :" + getFileName( uri), Toast.LENGTH_LONG ).show();

                Intent intent = new Intent(Categories.this, ARScreen.class );
                startActivity( intent );

            }
        }
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        result = result.substring(0,result.length() - 4 );
        return result;
    }
    /**
    * This method opens help screen
    */
    public void openHelp(){
        Intent intent = new Intent(this, HelpScreen.class );
        startActivity( intent );
    }

    /**
     * This method returns home page
     */
    public void openHome(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity( intent );
    }

    /**
     * This method opens search screen
     */
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
        searchView.setQueryHint( "Search in categories" );
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
    public boolean onOptionsItemSelected(MenuItem item) {

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
                return super.onOptionsItemSelected( item );
        }
    }
}
