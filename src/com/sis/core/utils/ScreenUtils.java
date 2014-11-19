package com.sis.core.utils;

import android.content.Context;

import com.sis.core.App;

public class ScreenUtils {

	public static float density() {
		return App.getDM().density;
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public static float screenWidth() {
		return App.getDM().widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @return
	 */
	public static float screenHeight() {
		return App.getDM().heightPixels;
	}

	/**
	 * dp转化为px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return (int) (dp * density() + 0.5f);
	}

	/**
	 * px转化为dp
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return (int) (px / density() + 0.5f);
	}

}
