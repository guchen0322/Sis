package com.sis.core.fragment.bigclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;

public class FDLFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fdlLayout = inflater.inflate(R.layout.fragment_fdl, container, false);
		return fdlLayout;
	}

}