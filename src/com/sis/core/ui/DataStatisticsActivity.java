package com.sis.core.ui;

import java.util.HashMap;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sis.core.App;
import com.sis.core.R;
import com.sis.core.entity.ResInfo;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.BigClassFragment;
import com.sis.core.fragment.base.BaseDataFragment;
import com.sis.core.listener.CyclePageChangeListener;
import com.sis.core.listener.DataCallBackListener;
import com.sis.core.ui.base.BaseFragmentActivity;
import com.sis.core.utils.ScreenUtils;

public class DataStatisticsActivity extends BaseFragmentActivity implements OnClickListener, CyclePageChangeListener, DataCallBackListener {

	private FragmentManager fragmentManager;

	private RelativeLayout titleRL;
	private ImageView backIV;
	private LinearLayout jizuSwitchLL;
	private TextView titleTV, descTV, dateTV, oneTV, unitTV, twoTV, thirdTV;
	private RelativeLayout thirdDataRL;

	private View jzfhLayout, fdmhLayout, gdmhLayout, fdlLayout;
	private ImageView jzfhTabIV, fdlTabIV, fdmhTabIV, gdmhTabIV;

	private PopupWindow mPopWin;
	private TextView currJZTV;
	private TextView selJZTV;

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
		titleRL = (RelativeLayout) findViewById(R.id.titleRL);
		backIV = (ImageView) findViewById(R.id.backIV);
		backIV.setOnClickListener(this);
		jizuSwitchLL = (LinearLayout) findViewById(R.id.jizuSwitchLL);
		jizuSwitchLL.setOnClickListener(this);
		currJZTV = (TextView) findViewById(R.id.currjzTV);

		titleTV = (TextView) findViewById(R.id.titleTV);
		descTV = (TextView) findViewById(R.id.descTV);
		dateTV = (TextView) findViewById(R.id.dateTV);
		oneTV = (TextView) findViewById(R.id.oneTV);
		unitTV = (TextView) findViewById(R.id.unitTV);
		twoTV = (TextView) findViewById(R.id.twoTV);
		thirdTV = (TextView) findViewById(R.id.thirdTV);

		thirdDataRL = (RelativeLayout) findViewById(R.id.thirdDataRL);

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
		selJZTV = (TextView) popView.findViewById(R.id.seljzTV);
		selJZTV.setOnClickListener(this);
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
		fragment.setDataCallBackListener(this);
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}

	private void clearSelection() {
		jzfhTabIV.setImageResource(R.drawable.jizufuhe_tab);
		fdmhTabIV.setImageResource(R.drawable.fadianmeihao_tab);
		gdmhTabIV.setImageResource(R.drawable.gongdianmeihao_tab);
		fdlTabIV.setImageResource(R.drawable.fadianliang_tab);
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
				selJZTV.setWidth(px_100);
				int xoff = (int) ScreenUtils.screenWidth() - px_100;
				mPopWin.showAsDropDown(titleRL, xoff - 5, 5);
			}
			break;
		case R.id.seljzTV:
			String currJZ = currJZTV.getText().toString();
			String selJZ = selJZTV.getText().toString();
			currJZTV.setText(selJZ);
			selJZTV.setText(currJZ);
			if (mPopWin != null)
				mPopWin.dismiss();

			// 重新请求
			HashMap<Integer, BaseDataFragment> mFragments = App.getInstance().getmFragments();
			Entry<Integer, BaseDataFragment> firstEntry = mFragments.entrySet().iterator().next();
			firstEntry.getValue().fetchObjectData();
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
			thirdDataRL.setVisibility(View.GONE);
			break;
		// 日
		case 1:
			descTV.setText("日统计数据");
			thirdDataRL.setVisibility(View.GONE);
			break;
		// 周
		case 2:
			descTV.setText("周统计数据");
			thirdDataRL.setVisibility(View.VISIBLE);
			break;
		// 月
		case 3:
			descTV.setText("月统计数据");
			thirdDataRL.setVisibility(View.VISIBLE);
			break;
		// 季
		case 4:
			descTV.setText("季统计数据");
			thirdDataRL.setVisibility(View.GONE);
			break;
		// 年
		case 5:
			descTV.setText("年统计数据");
			thirdDataRL.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void dataCallBack(final ResInfo resInfo) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				double value = Double.valueOf(resInfo.getValue());
				double difference = Double.valueOf(resInfo.getDifference());
				double percentage = Double.valueOf(resInfo.getPercentage());

				if (difference >= 0) {
					oneTV.setTextColor(getResources().getColor(R.color.fen_data_color));
					twoTV.setTextColor(getResources().getColor(R.color.fen_data_color));
					thirdTV.setTextColor(getResources().getColor(R.color.fen_data_color));
					oneTV.setText(String.format("%.2f", value));
					twoTV.setText(String.format("%.2f", difference));
					thirdTV.setText(String.format("%.2f", percentage) + "%");
				} else {
					oneTV.setTextColor(getResources().getColor(R.color.other_data_color));
					twoTV.setTextColor(getResources().getColor(R.color.other_data_color));
					thirdTV.setTextColor(getResources().getColor(R.color.other_data_color));
					oneTV.setText(String.format("%.2f", value));
					twoTV.setText(String.format("%.2f", difference));
					thirdTV.setText(String.format("%.2f", percentage) + "%");
				}

				// 设置Date
				if (!"null".equals(resInfo.getDate())) {
					dateTV.setText(resInfo.getDate());
				} else {
					dateTV.setText("");
				}
			}
		});
	}

}
