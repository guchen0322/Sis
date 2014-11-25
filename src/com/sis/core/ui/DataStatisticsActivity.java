package com.sis.core.ui;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.sis.core.R;
import com.sis.core.entity.ResInfo;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.BigClassFragment;
import com.sis.core.listener.CyclePageChangeListener;
import com.sis.core.net.SISHttpClient;
import com.sis.core.ui.base.BaseFragmentActivity;
import com.sis.core.utils.JsonUtil;
import com.sis.core.utils.ScreenUtils;
import com.sis.core.widget.switchView.SwitchButton;

public class DataStatisticsActivity extends BaseFragmentActivity implements OnClickListener, CyclePageChangeListener {

	private FragmentManager fragmentManager;

	private RelativeLayout titleRL;
	private ImageView backIV;
	private LinearLayout jizuSwitchLL;
	private SwitchButton dataCompareSB;
	private TextView titleTV, descTV, oneTV, unitTV, twoTV, thirdTV;
	private RelativeLayout thirdDataRL;

	private View jzfhLayout, fdmhLayout, gdmhLayout, fdlLayout;
	private ImageView jzfhTabIV, fdlTabIV, fdmhTabIV, gdmhTabIV;

	private PopupWindow mPopWin;
	private TextView jizu;
	private TextView jizuTV;

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
		titleRL = (RelativeLayout) findViewById(R.id.titleRL);
		backIV = (ImageView) findViewById(R.id.backIV);
		backIV.setOnClickListener(this);
		jizuSwitchLL = (LinearLayout) findViewById(R.id.jizuSwitchLL);
		jizuSwitchLL.setOnClickListener(this);
		jizu = (TextView) findViewById(R.id.jizu);

		titleTV = (TextView) findViewById(R.id.titleTV);
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

		jzfhTabIV = (ImageView) findViewById(R.id.jzfhTabIV);
		fdlTabIV = (ImageView) findViewById(R.id.fdlTabIV);
		fdmhTabIV = (ImageView) findViewById(R.id.fdmhTabIV);
		gdmhTabIV = (ImageView) findViewById(R.id.gdmhTabIV);
		jzfhLayout = findViewById(R.id.jzfh_layout);
		fdmhLayout = findViewById(R.id.fdmh_layout);
		gdmhLayout = findViewById(R.id.gdmh_layout);
		fdlLayout = findViewById(R.id.fdl_layout);
		jzfhLayout.setOnClickListener(this);
		fdmhLayout.setOnClickListener(this);
		gdmhLayout.setOnClickListener(this);
		fdlLayout.setOnClickListener(this);

		initPopWin();
	}

	private void initPopWin() {
		View popView = getLayoutInflater().inflate(R.layout.juzu_switch_pop_win, null);
		jizuTV = (TextView) popView.findViewById(R.id.jizuTV);
		jizuTV.setOnClickListener(this);
		mPopWin = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		mPopWin.setTouchable(true);
		mPopWin.setOutsideTouchable(true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
	}

	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();

		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		BigClassFragment fragment = null;
		switch (index) {
		case 0:
			jzfhTabIV.setImageResource(R.drawable.jizufuhe_pressed_tab);
			fragment = BigClassFragment.newInstance(FragmentType.JZFH);
			break;
		case 1:
			fdlTabIV.setImageResource(R.drawable.fadianliang_press_tab);
			fragment = BigClassFragment.newInstance(FragmentType.FDL);
			break;
		case 2:
			fdmhTabIV.setImageResource(R.drawable.fadianmeihao_press_tab);
			fragment = BigClassFragment.newInstance(FragmentType.FDMH);
			break;
		case 3:
			gdmhTabIV.setImageResource(R.drawable.gongdianmeihao_press_tab);
			fragment = BigClassFragment.newInstance(FragmentType.GDMH);
			break;
		}

		fragment.setCyclePageChangeListener(this);
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}

	private void clearSelection() {
		jzfhTabIV.setImageResource(R.drawable.jizufuhe_tab);
		fdmhTabIV.setImageResource(R.drawable.fadianmeihao_tab);
		gdmhTabIV.setImageResource(R.drawable.gongdianmeihao_tab);
		fdlTabIV.setImageResource(R.drawable.fadianliang_tab);
	}

	private void getServerData() {
		SISHttpClient
				.get("http://oa.sygpp.com/SACSIS/HOUR/SACSIS/getallsacsisforgetparam?type=1&startTime=2014-09-26 09:00:00&endTime=2014-09-26 10:00:01",
						new BaseJsonHttpResponseHandler<ResInfo>() {

							@Override
							public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData,
									ResInfo errorResponse) {

							}

							@Override
							public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ResInfo resInfo) {

							}

							@Override
							protected ResInfo parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
								Log.i("zhang.h", rawJsonData);
								return JsonUtil.getResInfo(rawJsonData);
							}
						});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backIV:
			back();
			break;
		case R.id.jizuSwitchLL:
			// 弹出popwin
			if (mPopWin != null) {
				int px_100 = ScreenUtils.dpToPx(getApplicationContext(), 100);
				jizuTV.setWidth(px_100);
				int xoff = (int) ScreenUtils.screenWidth() - px_100;
				mPopWin.showAsDropDown(titleRL, xoff - 5, 5);
			}
			break;
		case R.id.jizuTV:
			String currJZ = jizu.getText().toString();
			String selJZ = jizuTV.getText().toString();
			jizu.setText(selJZ);
			jizuTV.setText(currJZ);
			if (mPopWin != null)
				mPopWin.dismiss();
			break;
		case R.id.jzfh_layout:
			titleTV.setText(R.string.jizufuhe);
			unitTV.setText("MW");
			setTabSelection(0);
			break;
		case R.id.fdl_layout:
			titleTV.setText(R.string.fadianliang);
			unitTV.setText("万KWH");
			setTabSelection(1);
			break;
		case R.id.fdmh_layout:
			titleTV.setText(R.string.fadianmeihao);
			unitTV.setText("g/(kw.h)");
			setTabSelection(2);
			break;
		case R.id.gdmh_layout:
			titleTV.setText(R.string.gongdianmeihao);
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
