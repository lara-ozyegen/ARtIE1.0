package com.example.artie10;

import android.graphics.Path;

/**
 * @author Öykü, Lara, Yaren, Sarper, Berk, Onur, Enis
 * @version 1.0
 * @date 23/04/2020
 * This class creates the "brush"
 */
public class FingerPath {

    //properties
    public int color;
    public int strokeWidth;
    public Path path;

    // constructor
    public FingerPath( int color, int strokeWidth, Path path ) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
