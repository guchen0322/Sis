package com.sis.core;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.sis.core.utils.ActivityMgrUtils;
import com.sis.core.utils.PreferenceUtils;

/**
 * Main application
 * 
 * @author Simon
 * 
 */
public class App extends Application {
	private static App singleton;
	private static Context mContext;
	private static ActivityMgrUtils activityManager;
	private static PreferenceUtils preferenceUtils;
	private static DisplayMetrics DM;

	@Override
	public void onCreate() {
		super.onCreate();

		init();
	}

	public static App getInstance() {
		return singleton;
	}

	public static Context getContext() {
		return mContext;
	}

	public static ActivityMgrUtils getActivityManager() {
		return activityManager;
	}

	public static PreferenceUtils getPreferenceUtils() {
		return preferenceUtils;
	}

	public static DisplayMetrics getDM() {
		return DM;
	}

	/**
	 * Intialize
	 */
	private void init() {
		singleton = this;
		mContext = this.getApplicationContext();

		activityManager = new ActivityMgrUtils();
		preferenceUtils = new PreferenceUtils(mContext);
		DM = getResources().getDisplayMetrics();
	}

}
