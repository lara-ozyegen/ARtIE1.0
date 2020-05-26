package com.example.artie10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 23/04/2020
 * This class provides information about user's profile and includes features log out and delete
 */

public class Profile extends AppCompatActivity {
    private Button delete_button;
    private FirebaseAuth mFirebaseAuth;
    private EditText mailText;
    private EditText usernameText;
    private Button logOutButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile); // linking class with xml

        //initializing properties
        mFirebaseAuth = FirebaseAuth.getInstance(); // getting user info from database

        //getting the user's credentials and inserting them
        mailText = findViewById( R.id.editEmail );
        if( mFirebaseAuth.getCurrentUser().getEmail() != null ) {
            mailText.setText( mFirebaseAuth.getCurrentUser().getEmail() );
            mailText.setEnabled( false );
        }

        usernameText = findViewById( R.id.editUsername );
        if ( mFirebaseAuth.getCurrentUser().getDisplayName() != null ){
            usernameText.setText( mFirebaseAuth.getCurrentUser().getDisplayName() );
            usernameText.setEnabled( false );
        }

        delete_button = ( Button ) findViewById( R.id.delete );
        //if delete button is clicked
        delete_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                deleteAccount();
            }
        });

        logOutButton = ( Button ) findViewById( R.id.logOutButton );
        //if log out button is clicked
        logOutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                logOut();
            }
        });

    }

    /**
     * a method which allows the user to delete the account
     */
    public void deleteAccount() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential( "user@example.com", "password1234" );

        // Prompt the user to re-provide their sign-in credentials
        if ( user != null ) {
            user.reauthenticate( credential )
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( @NonNull Task<Void> task ) {
                            user.delete();
                            Toast.makeText( Profile.this, "Your account has been deleted.", Toast.LENGTH_SHORT ).show();
                        }
                    });
        }
        backToMain();
    }

    /**
     * a method which allows the user to log out from the current account
     */
    public void logOut(){
        mFirebaseAuth.signOut();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        //if the user is still logged in
        if( user != null ) {
            Toast.makeText( Profile.this, "Could not log out. Please try again.", Toast.LENGTH_SHORT).show();
        }

        //if the user is no longer logged in
        else{
            Toast.makeText( Profile.this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
        }

        //going back to the main page
        backToMain();
    }

    /**
     * a method which changes the window to main screen.
     */
    public void backToMain(){
        Intent backToMain = new Intent(Profile.this, MainActivity.class );
        startActivity( backToMain );
    }
}
