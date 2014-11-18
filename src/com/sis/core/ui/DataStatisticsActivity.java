package com.sis.core.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sis.core.R;
import com.sis.core.fragment.FDLFragment;
import com.sis.core.fragment.FDMHFragment;
import com.sis.core.fragment.GDMHFragment;
import com.sis.core.fragment.JZFHFragment;
import com.sis.core.ui.base.BaseFragmentActivity;

public class DataStatisticsActivity extends BaseFragmentActivity implements OnClickListener {

	private FragmentManager fragmentManager;
	private JZFHFragment jzfhFragment;
	private FDMHFragment fdmhFragment;
	private GDMHFragment gdmhFragment;
	private FDLFragment fdlFragment;

	private ImageView backIV;
	private View jzfhLayout;
	private View fdmhLayout;
	private View gdmhLayout;
	private View fdlLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_statistics);

		// 初始化布局元素
		initViews();

		fragmentManager = getSupportFragmentManager();

		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	private void initViews() {
		backIV = (ImageView) findViewById(R.id.backIV);
		backIV.setOnClickListener(this);

		jzfhLayout = findViewById(R.id.jzfh_layout);
		fdmhLayout = findViewById(R.id.fdmh_layout);
		gdmhLayout = findViewById(R.id.gdmh_layout);
		fdlLayout = findViewById(R.id.fdl_layout);
		jzfhLayout.setOnClickListener(this);
		fdmhLayout.setOnClickListener(this);
		gdmhLayout.setOnClickListener(this);
		fdlLayout.setOnClickListener(this);
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
			if (jzfhFragment == null) {
				jzfhFragment = new JZFHFragment();
				transaction.add(R.id.content, jzfhFragment);
			} else {
				transaction.show(jzfhFragment);
			}
			break;
		case 1:
			if (fdmhFragment == null) {
				fdmhFragment = new FDMHFragment();
				transaction.add(R.id.content, fdmhFragment);
			} else {
				transaction.show(fdmhFragment);
			}
			break;
		case 2:
			if (gdmhFragment == null) {
				gdmhFragment = new GDMHFragment();
				transaction.add(R.id.content, gdmhFragment);
			} else {
				transaction.show(gdmhFragment);
			}
			break;
		case 3:
			if (fdlFragment == null) {
				fdlFragment = new FDLFragment();
				transaction.add(R.id.content, fdlFragment);
			} else {
				transaction.show(fdlFragment);
			}
			break;
		}
		transaction.commit();
	}

	private void clearSelection() {

	}

	private void hideFragments(FragmentTransaction transaction) {
		if (jzfhFragment != null) {
			transaction.hide(jzfhFragment);
		}
		if (fdmhFragment != null) {
			transaction.hide(fdmhFragment);
		}
		if (gdmhFragment != null) {
			transaction.hide(gdmhFragment);
		}
		if (fdlFragment != null) {
			transaction.hide(fdlFragment);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backIV:
			back();
			break;
		case R.id.jzfh_layout:
			setTabSelection(0);
			break;
		case R.id.fdmh_layout:
			setTabSelection(1);
			break;
		case R.id.gdmh_layout:
			setTabSelection(2);
			break;
		case R.id.fdl_layout:
			setTabSelection(3);
			break;
		}
	}

}
