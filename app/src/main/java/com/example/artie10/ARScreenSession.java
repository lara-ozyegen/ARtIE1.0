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

public class ARScreenSession extends ARScreen {

    ImageButton b;
    public ARScreenSession() {
        super();
        b = (ImageButton) findViewById(R.id.info_button);
       // b.setVisibility(View.VISIBLE);

    }


}
