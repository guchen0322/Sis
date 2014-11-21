package com.sis.core.fragment.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.sis.core.utils.ScreenUtils;

public class BaseFragment extends Fragment {

	protected static final String KEY_FRAGMENT_TYPE = "key_fragment_type";
	protected static final String[] TITLES = { "分时", "日统计", "周统计", "月统计", "季统计", "年统计" };
	protected static final int SHOW_TITLE_COUNT = 5;

	protected static final int JZFH_COLOR = Color.rgb(0, 188, 13);
	protected static final int FDL_COLOR = Color.rgb(171, 46, 236);
	protected static final int FDMH_COLOR = Color.rgb(219, 122, 18);
	protected static final int GDMH_COLOR = Color.rgb(243, 68, 129);

	protected FragmentActivity mActivity;
	protected LayoutInflater mInflater;
	protected int screenW;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity() == null ? mActivity : getActivity());
		screenW = (int) ScreenUtils.screenWidth();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity != null) {
			mActivity = (FragmentActivity) activity;
		}
	}

	public void forwardActivity(Class<?> cls) {
		Intent intent = new Intent(mActivity, cls);
		startActivity(intent);
	}
}
