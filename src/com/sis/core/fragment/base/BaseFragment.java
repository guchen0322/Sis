package com.sis.core.fragment.base;

import com.sis.core.utils.ScreenUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

public class BaseFragment extends Fragment {

	protected static final int SHOW_TITLE_COUNT = 5;

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
