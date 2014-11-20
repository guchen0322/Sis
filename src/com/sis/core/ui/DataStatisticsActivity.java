package com.sis.core.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sis.core.R;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.bigclass.FDLFragment;
import com.sis.core.fragment.bigclass.FDMHFragment;
import com.sis.core.fragment.bigclass.GDMHFragment;
import com.sis.core.fragment.bigclass.JZFHFragment;
import com.sis.core.listener.CyclePageChangeListener;
import com.sis.core.ui.base.BaseFragmentActivity;
import com.sis.core.widget.switchView.SwitchButton;

public class DataStatisticsActivity extends BaseFragmentActivity implements OnClickListener, CyclePageChangeListener {

	private FragmentManager fragmentManager;
	private JZFHFragment jzfhFragment;
	private FDMHFragment fdmhFragment;
	private GDMHFragment gdmhFragment;
	private FDLFragment fdlFragment;

	private ImageView backIV;
	private SwitchButton dataCompareSB;
	private TextView descTV, oneTV, unitTV, twoTV, thirdTV;
	private RelativeLayout thirdDataRL;

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

		// 请求数据
		getServerData();
	}

	private void initViews() {
		backIV = (ImageView) findViewById(R.id.backIV);
		backIV.setOnClickListener(this);

		descTV = (TextView) findViewById(R.id.descTV);
		oneTV = (TextView) findViewById(R.id.oneTV);
		unitTV = (TextView) findViewById(R.id.unitTV);
		twoTV = (TextView) findViewById(R.id.twoTV);
		thirdTV = (TextView) findViewById(R.id.thirdTV);

		thirdDataRL = (RelativeLayout) findViewById(R.id.thirdDataRL);
		dataCompareSB = (SwitchButton) findViewById(R.id.dataCompareSB);
		dataCompareSB.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(DataStatisticsActivity.this, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
			}
		});

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
				jzfhFragment.setCyclePageChangeListener(this);
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

	private void getServerData() {
		/*
		RequestParams params = new RequestParams();
		params.put("quid", "1");
		// params.put("startTime", "2014-09-26 09:00:00");
		// params.put("endTime", "2014-09-26 10:00:01");
		SISHttpClient.post("http://121.42.12.128/REST/question/getansersbyqid", params, new BaseJsonHttpResponseHandler<ResInfo>() {

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ResInfo errorResponse) {

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ResInfo resInfo) {
				if (resInfo != null) {

				}
			}

			@Override
			protected ResInfo parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				Log.i("zhang.h", rawJsonData);
				return JsonUtil.getResInfo(rawJsonData);
			}
		});
		
		SISHttpClient.get("http://121.42.12.128/REST/communication/getAllCommuncaion", new BaseJsonHttpResponseHandler<ResInfo>() {

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2, String arg3, ResInfo arg4) {
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2, ResInfo arg3) {
				
			}

			@Override
			protected ResInfo parseResponse(String arg0, boolean arg1) throws Throwable {
				Log.i("zhang.h", arg0);
				return null;
			}
		});
		*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backIV:
			back();
			break;
		case R.id.jzfh_layout:
			unitTV.setText("MW");
			setTabSelection(0);
			break;
		case R.id.fdl_layout:
			unitTV.setText("万KWH");
			setTabSelection(1);
			break;
		case R.id.fdmh_layout:
			unitTV.setText("g/(kw.h)");
			setTabSelection(2);
			break;
		case R.id.gdmh_layout:
			unitTV.setText("g/(kw.h)");
			setTabSelection(3);
			break;
		}
	}

	@Override
	public void cycleChange(FragmentType fragmentType, int position) {
		switch (position) {
		// 分时
		case 0:
			descTV.setText("分时数据");
			oneTV.setTextColor(getResources().getColor(R.color.fen_data_color));
			twoTV.setTextColor(getResources().getColor(R.color.fen_data_color));
			thirdTV.setTextColor(getResources().getColor(R.color.fen_data_color));
			thirdDataRL.setVisibility(View.GONE);
			break;
		// 日
		case 1:
			descTV.setText("日统计数据");
			oneTV.setTextColor(getResources().getColor(R.color.other_data_color));
			twoTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdDataRL.setVisibility(View.GONE);
			break;
		// 周
		case 2:
			descTV.setText("周统计数据");
			oneTV.setTextColor(getResources().getColor(R.color.other_data_color));
			twoTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdDataRL.setVisibility(View.VISIBLE);
			break;
		// 月
		case 3:
			descTV.setText("月统计数据");
			oneTV.setTextColor(getResources().getColor(R.color.other_data_color));
			twoTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdDataRL.setVisibility(View.VISIBLE);
			break;
		// 季
		case 4:
			descTV.setText("季统计数据   2014年 3季度");
			oneTV.setTextColor(getResources().getColor(R.color.other_data_color));
			twoTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdDataRL.setVisibility(View.GONE);
			break;
		// 年
		case 5:
			descTV.setText("年统计数据   2014年");
			oneTV.setTextColor(getResources().getColor(R.color.other_data_color));
			twoTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdTV.setTextColor(getResources().getColor(R.color.other_data_color));
			thirdDataRL.setVisibility(View.GONE);
			break;
		}
	}

}
