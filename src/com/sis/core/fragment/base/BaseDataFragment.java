package com.sis.core.fragment.base;

import com.sis.core.R;

import android.os.Bundle;
import android.widget.TextView;

public abstract class BaseDataFragment extends BaseFragment {

	private boolean isReadyToFetchObjectData;

	public abstract void fetchObjectData();

	public boolean isReadyToFetchObjectData() {
		return isReadyToFetchObjectData;
	}

	protected TextView currJZTV;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currJZTV = (TextView) mActivity.findViewById(R.id.currjzTV);
	}

	@Override
	public void onResume() {
		super.onResume();
		isReadyToFetchObjectData = true;
	}

}
