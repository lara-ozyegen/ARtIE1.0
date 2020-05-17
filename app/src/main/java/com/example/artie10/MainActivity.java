package com.example.artie10;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.artie10.View.AboutUs;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //properties
    private Button button;
    private Button button2;
    private Button sessionButton;
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private FirebaseAuth mFirebaseAuth;
    private static boolean path;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        imageButton = ( ImageButton ) findViewById( R.id.help );
        imageButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                openHelp();
            }
        });

        imageButton2 = ( ImageButton ) findViewById( R.id.profileIcon );
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( mFirebaseAuth.getCurrentUser() != null )
                    openProfile();
                else
                    openSignIn();
                path = false;
            }
        });

        button = ( Button )findViewById( R.id.freeSession );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                openCategories();
                path = true;
            }

        });
        button2 = ( Button )findViewById( R.id.aboutUs );
        button2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                openAboutUs();
            }
        });

        sessionButton = ( Button ) findViewById( R.id.sessionMode );
        sessionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v)  {
                if( mFirebaseAuth.getCurrentUser() != null )
                    showPopup( v );
                else
                    openSignIn();
                path = false;
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

}
    public void showPopup( View v ) {
        PopupMenu popup = new PopupMenu(this, v );
        popup.setOnMenuItemClickListener( this );
        popup.inflate( R.menu.popup_menu );
        popup.show();
    }

    @Override
    public boolean onMenuItemClick( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.newSession:
                openCategories();
                return true;
            case R.id.joinSession:
                openRetrieveVideo();
                return true;
            default:
                return false;
        }
    }

    public void openHelp(){
        Intent intent = new Intent(this, HelpScreen.class );
        startActivity( intent );
    }
    public void openCategories(){
        Intent intent = new Intent(this, Categories.class );
        startActivity( intent );
    }

    public void openJoinSession() {
        Intent intent = new Intent(this, RetrieveVideo.class);
        startActivity( intent );
    }

    public void openSignIn() {
        Intent intent = new Intent( this, SignIn.class );
        startActivity( intent );
    }

    public void openAboutUs() {
        Intent intent = new Intent(this, AboutUs.class );
        startActivity( intent );
    }

    public void openProfile() {
        Intent intent = new Intent(this, Profile.class );
        startActivity( intent );
    }

    public void openRetrieveVideo(){
        Intent intent = new Intent(this, RetrieveVideo.class );
        startActivity( intent );
    }

    /**
     * Gives information about the path that the user choose.
     * @return boolean (if freemode is clicked true else false)
     */
    public static boolean getPath() {
        return path;
    }

}
