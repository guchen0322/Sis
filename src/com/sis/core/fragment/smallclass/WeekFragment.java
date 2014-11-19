package com.sis.core.fragment.smallclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;

public class WeekFragment extends BaseFragment {

	public static WeekFragment newInstance() {
		WeekFragment fragment = new WeekFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fdlLayout = inflater.inflate(R.layout.fragment_week, container, false);
		return fdlLayout;
	}

}