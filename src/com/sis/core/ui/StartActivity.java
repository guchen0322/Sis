package com.sis.core.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

import com.sis.core.App;
import com.sis.core.R;
import com.sis.core.ui.base.BaseActivity;

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
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);

		// 退出栈
		App.getActivityManager().popActivity(this);
	}

}