/*
 * 操作处理String的方法工具
 */

package com.once.utils;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class StringUtils {

	private static StringUtils mInstance = null;
	
	public static StringUtils getInstance(){
		if(null == mInstance){
			mInstance = new StringUtils();
		}
		return mInstance;
	}
	
	/**
	 * @description 获取一段字符串的字符个数（包含中英文，一个中文算2个字符）
	 * @param content
	 * @return
	 */
	public static int getCharacterNum(final String content) {
		if (null == content || "".equals(content)) {
			return 0;
		}else {
			return (content.length() + getChineseNum(content));
		}
	}

	/**
	* @description 返回字符串里中文字或者全角字符的个数
	* @param s 
	* @return
	*/
	public static int getChineseNum(String s) {
		int num = 0;
		char[] myChar = s.toCharArray();
		for (int i = 0; i < myChar.length; i++) {
			if ((char)(byte)myChar[i] != myChar[i]) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 隐藏键盘
	 * @param context
	 * @param view
	 */
	public static void hideSoftInput(Context context ,View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive()){
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘  
		}
	}
	//日期格式转换
	static SimpleDateFormat sdf;
	public static String Date2String(java.util.Date date){
		sdf = new SimpleDateFormat("E kk:mm MM月dd日");
		return sdf.format(date);
	}
}
