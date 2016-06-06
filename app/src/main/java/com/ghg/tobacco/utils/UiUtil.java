package com.ghg.tobacco.utils;

import android.content.Context;
import android.os.Handler;

/**
 * Created by 刘楠 on 2016/6/6 0006 10:59.
 *
 * 测试下
 */
public class UiUtil {

  public static Context mContext;
    public static Handler mHandler;

    public static void init( Context applicationContext ) {
       mContext=applicationContext;
        mHandler= new Handler();
    }


    public static Context getContext(){
        return mContext;
    }


}
