package com.example.artie10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 23/04/2020
 * This class creates a view for painting activity, and by adding this view to xml we add painting feature
 */
public class PaintView extends View {

    //properties
    public static final int BRUSH_SIZE = 10;
    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.TRANSPARENT;
    private static final float TOUCH_TOLERANCE = 4;

    private float mX, mY;

    private Path mPath;
    private Paint mPaint;

    private ArrayList<FingerPath> paths = new ArrayList<>();

    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private Bitmap mBitMap;
    private Canvas mCanvas;
    private Paint mBitMapPaint = new Paint(Paint.DITHER_FLAG);

    public boolean emboss;
    public boolean blur;

    // constructors
    public PaintView( Context context ) {
        this( context,null );
    }

    public PaintView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );

        // setting features
        mPaint = new Paint();
        mPaint.setAntiAlias( true);
        mPaint.setDither( true);
        mPaint.setColor( DEFAULT_COLOR);
        mPaint.setStyle( Paint.Style.STROKE);
        mPaint.setStrokeJoin( Paint.Join.ROUND);
        mPaint.setStrokeCap( Paint.Cap.ROUND);
        mPaint.setXfermode( null);
        mPaint.setAlpha( 0xff);
    }

    /**
     * a method which initializes lines
     */
    public void init( DisplayMetrics metrics ){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitMap = Bitmap.createBitmap( width,height,Bitmap.Config.ARGB_8888);
        //creating a canvas that we can paint to
        mCanvas = new Canvas( mBitMap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }

    @Override
    protected void onDraw( Canvas canvas ){
        canvas.save();
        mCanvas.drawColor( backgroundColor);

        for( FingerPath fp : paths ){
            mPaint.setColor( fp.color);
            mPaint.setStrokeWidth( fp.strokeWidth);
            mPaint.setMaskFilter( null);

            mCanvas.drawPath( fp.path, mPaint);
        }

        canvas.drawBitmap( mBitMap,0,0,mBitMapPaint);
        canvas.restore();
    }

    /**
     * a method which starts drawing when user touch
     */
    private void touchStart( float x, float y ){
        mPath = new Path();
        FingerPath fp = new FingerPath( currentColor, strokeWidth, mPath);
        paths.add( fp);

        mPath.reset();
        mPath.moveTo( x,y);
        mX = x;
        mY = y;

    }

    /**
     * a method which detects the moves
     */
    private void touchMove( float x, float y ){
        float dx = Math.abs( x - mX);
        float dy = Math.abs( y - mY);

        if( dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE ){
            mPath.quadTo( mX,mY,( x + mX ) / 2, ( y + mY ) / 2);
            mX = x;
            mY = y;
        }
    }

    /**
     * a method which adds a line
     */
    private void touchUp(){
        mPath.lineTo( mX, mY );
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ){
        float x = event.getX();
        float y = event.getY();

        switch( event.getAction() ){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
