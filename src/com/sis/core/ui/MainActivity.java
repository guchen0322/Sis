package com.sis.core.ui;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.sis.core.App;
import com.sis.core.R;
import com.sis.core.fragment.DataStatisticsTabFragment;
import com.sis.core.fragment.UserCenterTabFragment;
import com.sis.core.ui.base.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentManager fragmentManager;
	private DataStatisticsTabFragment dsFragment;
	private UserCenterTabFragment ucFragment;

	private View dsLayout, ucLayout;
	private ImageView dsTabIV, ucTabIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 

		// 初始化布局元素
		initViews();

		fragmentManager = getSupportFragmentManager();

		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	private void initViews() {
		dsTabIV = (ImageView) findViewById(R.id.dsTabIV);
		ucTabIV = (ImageView) findViewById(R.id.ucTabIV);
		dsLayout = findViewById(R.id.ds_layout);
		ucLayout = findViewById(R.id.uc_layout);
		dsLayout.setOnClickListener(this);
		ucLayout.setOnClickListener(this);
	}

	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();

		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);

		switch (index) {
		case 0:
			dsTabIV.setImageResource(R.drawable.data_statistics_press_tab);
			if (dsFragment == null) {
				// 如果DataStatisticsTabFragment为空，则创建一个并添加到界面上
				dsFragment = new DataStatisticsTabFragment();
				transaction.add(R.id.content, dsFragment);
			} else {
				// 如果DataStatisticsTabFragment不为空，则直接将它显示出来
				transaction.show(dsFragment);
			}
			break;
		case 1:
			ucTabIV.setImageResource(R.drawable.user_center_press_tab);
			if (ucFragment == null) {
				// 如果UserCenterTabFragment为空，则创建一个并添加到界面上
				ucFragment = new UserCenterTabFragment();
				transaction.add(R.id.content, ucFragment);
			} else {
				// 如果UserCenterTabFragment不为空，则直接将它显示出来
				transaction.show(ucFragment);
			}
			break;

		default:
			break;
		}
		transaction.commit();
	}

	private void clearSelection() {
		dsTabIV.setImageResource(R.drawable.data_statistics_tab);
		ucTabIV.setImageResource(R.drawable.user_center_tab);
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (dsFragment != null) {
			transaction.hide(dsFragment);
		}
		if (ucFragment != null) {
			transaction.hide(ucFragment);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ds_layout:
			setTabSelection(0);
			break;
		case R.id.uc_layout:
			setTabSelection(1);
			break;
		}
	}

	// ----------------------------------------------------- 定义一个变量，来标识是否退出 begin
	private static boolean isExit = false;

	static class mHandler extends Handler {
		WeakReference<MainActivity> mActivity;

		mHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(MainActivity.this, R.string.exit_app, Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			new mHandler(this).sendEmptyMessageDelayed(0, 2000);
		} else {
			shutDown();
		}
	}

	private void shutDown() {
		// 移除所有Activity
		App.getActivityManager().popAllActivity();
	}

	// ----------------------------------------------------- 定义一个变量，来标识是否退出 end

}
