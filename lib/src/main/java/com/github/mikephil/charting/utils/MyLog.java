package com.github.mikephil.charting.utils;

import android.util.Log;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/18 10:18
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MyLog {
    private static boolean  isDebug=false;
    public static void log(String msg){
        if (isDebug){
            Log.d("vivi",msg);
        }
    }
}
