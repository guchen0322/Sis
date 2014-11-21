package com.sis.core;

import java.util.HashMap;
import java.util.Map;

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
	private static Map<String,String> accounts;

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

	public static Map<String, String> getAccounts() {
		return accounts;
	}

	public static void setAccounts(Map<String, String> accounts) {
		App.accounts = accounts;
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
		
		accounts = new HashMap<String, String>();
		accounts.put("Admin", "sygpassword");
		accounts.put("Sunl", "sygpassword");
		accounts.put("Dongmh", "sygpassword");
		accounts.put("Jizw", "sygpassword");
	}

}
