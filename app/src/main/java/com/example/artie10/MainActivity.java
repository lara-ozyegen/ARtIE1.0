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

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 20/04/2020
 * MainActivity - Welcome screen. It has two distinct mode buttons, help, about us and profile.
 */
public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //properties
    private Button button;
    private Button aboutUs;
    private Button sessionButton;
    private ImageButton help;
    private ImageButton profile;
    private FirebaseAuth mFirebaseAuth;
    private static boolean path;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //get user status from firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        //display help screen
        help = ( ImageButton ) findViewById( R.id.help );
        help.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                openHelp();
            }
        });

        //display profile if user created an account else open signIn & signUp.
        profile = ( ImageButton ) findViewById( R.id.profileIcon );
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( mFirebaseAuth.getCurrentUser() != null )
                    openProfile();
                else
                    openSignIn();
                path = false;
            }
        });

        //display about us page
        aboutUs = ( Button )findViewById( R.id.aboutUs );
        aboutUs.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                openAboutUs();
            }
        });

        //display freeMode categories
        button = ( Button )findViewById( R.id.freeSession );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                openCategories();
                path = true;
            }
        });

        //display SessionMode popup menu if user has an account else open signIn & signUp
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
}

    /**
     * popup menu that involves join session and new session buttons
     * @param v popup menu view
     * */
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

    /**
     * Gives information about the path that the user choose.
     * @return boolean (if freemode is clicked true else false)
     */
    public static boolean getPath() {
        return path;
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
}
