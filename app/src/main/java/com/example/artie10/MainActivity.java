package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


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

        imageButton = ( ImageButton )findViewById( R.id.help );
        imageButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                openHelp();
            }
        });

        imageButton2 = ( ImageButton )findViewById( R.id.imageView6 );
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if( mFirebaseAuth.getCurrentUser() != null )
                    openProfile();
                else
                    openLoginScreen();
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
                    openLoginScreen();
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
                //openLogin();
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
    /*
    public void openLogin() {
        Intent intent = new Intent(this, JoinPopup.class);
        startActivity(intent);
    }
     */
    public void openLoginScreen() {
        Intent intent = new Intent(this, LoginScreen.class );
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

    /**
     * Gives information about the path that the user choose.
     * @return boolean (if freemode is clicked true else false)
     */
    public static boolean getPath() {
        return path;
    }

}
