package com.sis.core.ui;

import com.sis.core.R;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

public class TabHostActivity extends Activity implements
		OnCheckedChangeListener {
	private TabHost tabHost;
	private Intent certificateIntent;
	private Intent feeIntent;
	private Intent scoreIntent;
	private Intent studyIntent;
	private Intent moreIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		// tabHost = getTabHost();
		tabHost = (TabHost) findViewById(R.id.my_tabhost);
		LocalActivityManager groupActivity = new LocalActivityManager(this,
				false);
		groupActivity.dispatchCreate(savedInstanceState);
		tabHost.setup(groupActivity);
		initIntent();
		addSpec();
		((RadioGroup) findViewById(R.id.tab_radiogroup))
				.setOnCheckedChangeListener(this);
	}

	/**
	 * 初始化各个tab标签对应的intent
	 */
	private void initIntent() {
		studyIntent = new Intent(this, BaseActivity.class);
		scoreIntent = new Intent(this, ScoreActivity.class);
		feeIntent = new Intent(this, FeeActivity.class);
		certificateIntent = new Intent(this, CertificateActivity.class);
		moreIntent = new Intent(this, MoreActivity.class);
	}

	/**
	 * 为tabHost添加各个标签项
	 */
	private void addSpec() {
		tabHost.addTab(this.buildTagSpec("tab_study", R.string.study_progress,
				R.drawable.account01, studyIntent));
		tabHost.addTab(this.buildTagSpec("tab_score", R.string.test_score,
				R.drawable.account02, scoreIntent));
		tabHost.addTab(this.buildTagSpec("tab_fee", R.string.fee_pay,
				R.drawable.account03, feeIntent));
		tabHost.addTab(this.buildTagSpec("tab_certificate",
				R.string.certificate_grant, R.drawable.account04,
				certificateIntent));
		tabHost.addTab(this.buildTagSpec("tab_more", R.string.more,
				R.drawable.account05, moreIntent));
	}

	/**
	 * 自定义创建标签项的方法
	 * 
	 * @param tagName
	 *            标签标识
	 * @param tagLable
	 *            标签文字
	 * @param icon
	 *            标签图标
	 * @param content
	 *            标签对应的内容
	 * @return
	 */
	private TabHost.TabSpec buildTagSpec(String tagName, int tagLable,
			int icon, Intent content) {
		return tabHost
				.newTabSpec(tagName)
				.setIndicator(getResources().getString(tagLable),
						getResources().getDrawable(icon)).setContent(content);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button_study:
			tabHost.setCurrentTabByTag("tab_study");
			Toast.makeText(this, "tab_study", 1000).show();
			break;
		case R.id.radio_button_score:
			tabHost.setCurrentTabByTag("tab_score");
			Toast.makeText(this, "tab_score", 1000).show();
			break;
		case R.id.radio_button_certificate:
			tabHost.setCurrentTabByTag("tab_certificate");
			Toast.makeText(this, "tab_certificate", 1000).show();
			break;
		case R.id.radio_button_fee:
			tabHost.setCurrentTabByTag("tab_fee");
			Toast.makeText(this, "tab_fee", 1000).show();
			break;
		case R.id.radio_button_more:
			tabHost.setCurrentTabByTag("tab_more");
			Toast.makeText(this, "tab_more", 1000).show();
			break;
		}
	}
}
