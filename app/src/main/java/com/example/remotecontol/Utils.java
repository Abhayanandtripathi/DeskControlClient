package com.example.remotecontol;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Utils {
    public static float getDisplacementFromCentre(float x, float y, float centerX, float centreY){
        return (float) sqrt(pow(x-centerX,2) + pow(y-centreY, 2));
    }
}
