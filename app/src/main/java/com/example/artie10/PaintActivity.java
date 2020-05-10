package com.example.artie10;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.widget.ImageButton;

        import com.example.artie10.PaintView;

public class PaintActivity extends AppCompatActivity {

    //proeprties
    private PaintView paintView;
    ImageButton pencil;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_paint );

        pencil = (ImageButton) findViewById(R.id.pencil);
        pencil.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {
                        returnAR();
                    }

                    }
        );

       paintView = ( PaintView ) findViewById( R.id.paintView );
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( metrics );
        paintView.init( metrics );
    }

    public void returnAR(){
        finish();
    }
}