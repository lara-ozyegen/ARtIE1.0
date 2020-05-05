package com.example.artie10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private EditText password;
    private EditText email;
    private Button signUp;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registeration );

        password = findViewById( R.id.password );
        email = findViewById( R.id.email_address );
        signUp = findViewById( R.id.register_button );
        mFirebaseAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
                if ( strEmail.isEmpty() ) {
                    email.setError( "Please enter a valid e-mail address." );
                    email.requestFocus();
                }
                else if ( strPassword.isEmpty() ){
                    password.setError( "Please enter a valid password." );
                    password.requestFocus();
                }
                else if ( strEmail.isEmpty() && strPassword.isEmpty() ){
                    Toast.makeText( Registration.this, "Fields are empty!", Toast.LENGTH_SHORT ).show();
                }
                else if ( !( strEmail.isEmpty() && strPassword.isEmpty() ) ){
                    mFirebaseAuth.createUserWithEmailAndPassword( strEmail, strPassword ).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task ) {
                            if ( task.isSuccessful() ){
                                startActivity( new Intent( Registration.this, Categories.class ) );
                            }
                            else{
                                Toast.makeText( Registration.this, "Could not create account, please try again.", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText( Registration.this, "Nani??! Some error??!", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}
