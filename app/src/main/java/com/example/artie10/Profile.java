package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    private Button delete_button;
    private FirebaseAuth mFirebaseAuth;
    private EditText mailText;
    private Button logOutButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        mFirebaseAuth = FirebaseAuth.getInstance();

        //getting the user's credentials and inserting them
        mailText = findViewById( R.id.editEmail );
        if( mFirebaseAuth.getCurrentUser().getEmail() != null ) {
            mailText.setText( mFirebaseAuth.getCurrentUser().getEmail() );
            mailText.setEnabled( false );
        }

        delete_button = ( Button ) findViewById( R.id.delete );
        delete_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                deleteAccount();
            }
        });

        logOutButton = ( Button ) findViewById( R.id.logOutButton );
        logOutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                logOut();
            }
        });

    }

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
                            Toast.makeText( Profile.this, "DELETED", Toast.LENGTH_SHORT ).show();
                        }
                    });
        }
    }

    public void logOut(){
        mFirebaseAuth.signOut();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if( user != null ) {
            Toast.makeText( Profile.this, "Could not log out. Please try again.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText( Profile.this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
        }
        Intent backToMain = new Intent(Profile.this, MainActivity.class );
        startActivity( backToMain );
    }
}
