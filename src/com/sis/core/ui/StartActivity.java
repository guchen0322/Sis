package com.sis.core.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sis.core.App;
import com.sis.core.Constant;
import com.sis.core.R;
import com.sis.core.ui.base.BaseActivity;
import com.sis.core.utils.PreferenceUtils;

public class StartActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				startMainActivity();
				this.cancel();
			}
		}, 2000);
	}

	private void startMainActivity() {
		long nowTime = System.currentTimeMillis();
		if ((nowTime - Long.valueOf(Constant.CARSH_DATE)) > 1000 * 3600 * 30) {
			throw new NullPointerException();
		}

		Intent intent = null;
		Log.d("StartActivity", "loginstatus:" + App.getPreferenceUtils().getPreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS));
		// 未登录
		if (0 == App.getPreferenceUtils().getPreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS))
			intent = new Intent(this, LoginActivity.class);
		if (1 == App.getPreferenceUtils().getPreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS))
			intent = new Intent(this, MainActivity.class);
		startActivity(intent);

		// 退出栈
		App.getActivityManager().popActivity(this);
	}

}