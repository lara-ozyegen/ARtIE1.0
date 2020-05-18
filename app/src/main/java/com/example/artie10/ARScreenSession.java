package com.example.artie10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Lara, Oyku, Sarper, Berk, Enis, Onur, Yaren
 * @date 01/05/2020
 * @version 1.0
 * ARScreenSession - session mode's AR screen
 */
public class ARScreenSession extends ARScreen {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        infoButtonSettings( true, true );
    }

}
