package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 24/04/2020
 * This class provides the sign up feature
 */
public class SignUp extends AppCompatActivity {

    //properties
    private EditText password;
    private EditText email;
    private EditText username;
    private Button signUp;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up);

        //so sign up will be a pop-up window
        makeItPopUp();

        //initializing properties
        password = findViewById( R.id.password );
        email = findViewById( R.id.email_address );
        username = findViewById( R.id.username);
        signUp = (Button) findViewById( R.id.signUpButton );
        mFirebaseAuth = FirebaseAuth.getInstance();

        //if sign up button is clicked
        signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                signUp();
            }
        });
    }

    /**
     * a method which makes the related window a pop-up.
     */
    public void makeItPopUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        int width = ( int ) ( dm.widthPixels * .6 );
        int height = ( int ) ( dm.heightPixels * .7 );

        getWindow().setLayout( width, height );
    }

    /**
     * a method which allows the user to create an account
     */
    public void signUp(){
         String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        String strUsername = username.getText().toString().trim();

        //if e-mail field is empty
        if ( strEmail.isEmpty() ) {
            email.setError( "Please enter a valid e-mail address." );
            email.requestFocus();
        }

        //if password field is empty
        else if ( strPassword.isEmpty() ){
            password.setError( "Please enter a valid password." );
            password.requestFocus();
        }

        //if username field is empty
        else if ( strUsername.isEmpty() ){
            username.setError( "Please enter a valid username." );
            username.requestFocus();
        }

        //if the fields are filled
        else if ( !( strEmail.isEmpty() && strPassword.isEmpty() && strUsername.isEmpty() ) ){
            mFirebaseAuth.createUserWithEmailAndPassword( strEmail, strPassword ).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task ) {

                    //if sign up is successful
                    if ( task.isSuccessful() ){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName( strUsername ).build();
                        user.updateProfile( profileUpdates ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Log.d(TAG, "User profile updated.");
                                }
                            }
                        });
                        startActivity( new Intent( SignUp.this, MainActivity.class ) );
                    }

                    //if sign up is failed
                    else{
                        Toast.makeText( SignUp.this, "Could not create account, please try again.", Toast.LENGTH_SHORT ).show();
                    }
                }
            });
        }

        //if some other non-predicted error has occurred
        else {
            Toast.makeText( SignUp.this, "Nani??! Some error??!", Toast.LENGTH_SHORT ).show();
        }
    }
}
