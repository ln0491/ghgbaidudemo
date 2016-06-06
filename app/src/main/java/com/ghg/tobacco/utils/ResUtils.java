package com.ghg.tobacco.utils;

import android.content.Context;

/**
 * Created by weizhi.zhu on 2016/5/13.
 */
public class ResUtils {

    public static int getColor(Context context,int colorId){
        return context.getResources().getColor(colorId);
    }
    public static String getString(Context context,int stringId){
        return context.getResources().getString(stringId);
    }
    public static float getDimension(Context context,int dimensionId){
        return context.getResources().getDimension(dimensionId);
    }
}
