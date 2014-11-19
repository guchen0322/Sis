package com.sis.core.fragment.bigclass;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sis.core.R;
import com.sis.core.adapter.CyclePagerAdapter;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.listener.CyclePageChangeListener;

public class JZFHFragment extends BaseFragment implements OnPageChangeListener, OnCheckedChangeListener {

	private String[] TITLES = new String[] { "分时", "日统计", "周统计", "月统计", "季统计", "年统计" };

	private HorizontalScrollView indicatorHSV;
	private RadioGroup indicatorRG;
	private ImageView indicatorIV;

	private ViewPager mViewPager;
	private int currentPosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View jzfhLayout = inflater.inflate(R.layout.fragment_jzfh, container, false);

		CyclePagerAdapter adapter = new CyclePagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) jzfhLayout.findViewById(R.id.pager);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);

		indicatorHSV = (HorizontalScrollView) jzfhLayout.findViewById(R.id.indicatorHSV);
		indicatorRG = (RadioGroup) jzfhLayout.findViewById(R.id.indicatorRG);
		indicatorRG.setOnCheckedChangeListener(this);
		indicatorIV = (ImageView) jzfhLayout.findViewById(R.id.indicatorIV);

		return jzfhLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initIndicatorTitle();
	}

	/**
	 * 初始化标题
	 */
	private void initIndicatorTitle() {
		if (null != TITLES && TITLES.length > 0) {
			indicatorHSV.setVisibility(View.VISIBLE);
			int indicatorWidth = TITLES.length >= SHOW_TITLE_COUNT ? screenW / SHOW_TITLE_COUNT : screenW / TITLES.length;

			// 初始化滑动下标的宽
			LayoutParams ivParams = indicatorIV.getLayoutParams();
			ivParams.width = indicatorWidth;
			indicatorIV.setLayoutParams(ivParams);
			indicatorIV.setBackgroundResource(R.color.index_line_color);
			indicatorRG.removeAllViews();
			for (int i = 0; i < TITLES.length; i++) {
				RadioButton rb = (RadioButton) mInflater.inflate(R.layout.radiogroup_item, null);
				rb.setId(i);
				rb.setText(TITLES[i]);
				rb.setSingleLine(true);
				rb.setLayoutParams(new LinearLayout.LayoutParams(indicatorWidth, LayoutParams.MATCH_PARENT));
				indicatorRG.addView(rb);
			}
		} else {
			indicatorHSV.setVisibility(View.GONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		// 监听切换动作
		mListener.cycleChange(FragmentType.JZFH, position);

		indicatorRG.check(position);

		// 执行位移动画
		RadioButton currRB = (RadioButton) indicatorRG.getChildAt(position);
		TranslateAnimation animation = new TranslateAnimation(currRB.getLeft(), currRB.getLeft(), 0f, 0f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(100);
		animation.setFillAfter(true);
		indicatorIV.startAnimation(animation);

		// 记录当前 下标的距最左侧的距离
		int currentIndicatorLeft = currRB.getLeft();
		int onecurrentIndicator = ((RadioButton) indicatorRG.getChildAt(2)).getLeft();

		// 位移到的位置
		if (TITLES != null && TITLES.length > SHOW_TITLE_COUNT) {
			indicatorHSV.smoothScrollTo((position > 1 ? currentIndicatorLeft : 0) - onecurrentIndicator, 0);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group.getChildAt(checkedId) != null) {
			mViewPager.setCurrentItem(checkedId);
			currentPosition = checkedId;
		}
	}

	private CyclePageChangeListener mListener;

	public void setCyclePageChangeListener(CyclePageChangeListener listener) {
		this.mListener = listener;
	}
}