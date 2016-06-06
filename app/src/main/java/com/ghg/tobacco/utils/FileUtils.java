package com.ghg.tobacco.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author 写文件的工具类
 * 
 */
public class FileUtils {


	public static String readAssetsFile(Context context,String name){
		String result = new String();
		String temp;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try{
			inputStream = context.getAssets().open(name);
			bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			while(bufferedReader!=null&&null!=(temp=bufferedReader.readLine()))
			{
				result+=temp;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if (inputStream!=null){
				try {
					inputStream.close();
					inputStream=null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			if (bufferedReader!=null){
				try {
					bufferedReader.close();
					bufferedReader=null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		return result;
	}
}
