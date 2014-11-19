package com.sis.core.fragment.bigclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;

public class GDMHFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View gdmhLayout = inflater.inflate(R.layout.fragment_gdmh, container, false);
		return gdmhLayout;
	}

}