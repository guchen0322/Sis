package com.sis.core.fragment.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

public class BaseFragment extends Fragment {

	public FragmentActivity mActivity;
	public LayoutInflater mInflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity() == null ? mActivity : getActivity());
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
