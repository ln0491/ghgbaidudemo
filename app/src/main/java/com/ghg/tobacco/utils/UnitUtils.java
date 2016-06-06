package com.ghg.tobacco.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
/**
 * 单位换算工具类
 * 	px  ：像素 <br>
	in  ：英寸<br>
	mm  ：毫米<br>
	pt  ：磅，1/72 英寸<br>
	dp  ：一个基于density的抽象单位，如果一个160dpi的屏幕，1dp=1px<br>
	dip ：等同于dp<br>
	sp  ：同dp相似，但还会根据用户的字体大小偏好来缩放。<br>
	建议使用sp作为文本的单位，其它用dip<br>
	布局时尽量使用单位dip，少使用px <br>
 */
public class UnitUtils {
	
	/**设备显示材质**/
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		WindowManager windowMgr = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics;
	}


	/**
	 * 获取屏幕宽
	 * @param activity
	 * @return
	 */
	public static  int getScreenWidth(Activity activity){
		WindowManager windowManager =activity.getWindowManager();
		Display defaultDisplay = windowManager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		defaultDisplay.getMetrics(dm);
		int screenWidth = dm.widthPixels;
//		screenHeight = dm.heightPixels;

		return screenWidth;
	}
	public static  int getScreenHeight(Activity activity){
		WindowManager windowManager =activity.getWindowManager();
		Display defaultDisplay = windowManager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		defaultDisplay.getMetrics(dm);
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}


	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * sp转换px
	 * @param spValue sp数值
	 * @return px数值
	 */
	public static int sp2px(Context context, float spValue) {
		DisplayMetrics mDisplayMetrics=getDisplayMetrics(context);
        return (int) (spValue * mDisplayMetrics.scaledDensity + 0.5f);
    }

	/**
	 * px转换sp
	 * @param pxValue px数值
	 * @return sp数值
	 */
    public static int px2sp(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics=getDisplayMetrics(context);
        return (int) (pxValue / mDisplayMetrics.scaledDensity + 0.5f);
    }

	/**
	 * dip转换px
	 * @param dipValue dip数值
	 * @return px数值
	 */
    public static int dip2px(Context context, int dipValue) {
		DisplayMetrics mDisplayMetrics=getDisplayMetrics(context);
        return (int) (dipValue * mDisplayMetrics.density + 0.5f);
    }
	/**
	 * px转换dip
	 * @param pxValue px数值
	 * @return dip数值
	 */
    public static int px2dip(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics=getDisplayMetrics(context);
        return (int) (pxValue / mDisplayMetrics.density + 0.5f);
    }
}
