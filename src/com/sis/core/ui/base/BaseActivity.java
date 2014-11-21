package com.sis.core.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sis.core.App;
import com.sis.core.R;

/**
 * Activity基类
 * 
 * @author Simon
 * 
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 添至栈
		App.getActivityManager().pushActivity(this);
	}

	// 跳转
	public void forwardActivity(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);

		// 退出栈
		App.getActivityManager().popActivity(this);
	}

	// 返回
	public void back() {
		App.getActivityManager().popActivity(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		App.getActivityManager().popActivity(this);
		overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
	}
	
}
