package com.ghg.tobacco.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences的工具类
 * 
 * @author wangfubin
 */
public class SharePreferenceUtils {
	private SharedPreferences sharedPreference;
	public void clear(){
		sharedPreference.edit().clear().commit();
	}
	/**
	 * 获取SharedPreferences实例对象
	 *
	 * @param context
	 * @return
	 */
	public SharePreferenceUtils(Context context, String name) {
		// return context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sharedPreference = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
	}

	/**
	 * 保存一个Boolean类型的值！
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putBoolean(String key, Boolean value) {
		Editor editor = sharedPreference.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * 保存一个int类型的值！
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putInt(Context context, String key, int value) {
		Editor editor = sharedPreference.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * 保存一个float类型的值！
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putFloat(Context context, String key, float value) {
		Editor editor = sharedPreference.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * 保存一个long类型的值！
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putLong(Context context, String key, long value) {
		Editor editor = sharedPreference.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * 保存一个String类型的值！
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putString(Context context, String key, String value) {
		Editor editor = sharedPreference.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * 获取String的value
	 *
	 * @param context
	 * @param key
	 *            名字
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public String getString(Context context, String key, String defValue) {
		return sharedPreference.getString(key, defValue);
	}

	/**
	 * 获取int的value
	 *
	 * @param context
	 * @param key
	 *            名字
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public int getInt(Context context, String key, int defValue) {
		return sharedPreference.getInt(key, defValue);
	}

	/**
	 * 获取float的value
	 *
	 * @param context
	 * @param key
	 *            名字
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public float getFloat(Context context, String key, Float defValue) {
		return sharedPreference.getFloat(key, defValue);
	}

	/**
	 * 获取boolean的value
	 *
	 * @param context
	 * @param key
	 *            名字
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public boolean getBoolean(Context context, String key, Boolean defValue) {
		return sharedPreference.getBoolean(key, defValue);
	}

	/**
	 * 获取long的value
	 *
	 * @param context
	 * @param key
	 *            名字
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public long getLong(Context context, String key, long defValue) {
		return sharedPreference.getLong(key, defValue);
	}

}
