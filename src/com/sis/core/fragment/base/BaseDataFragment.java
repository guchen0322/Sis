package com.sis.core.fragment.base;

public abstract class BaseDataFragment extends BaseFragment {

	private boolean isReadyToFetchObjectData;

	public abstract void fetchObjectData();

	public boolean isReadyToFetchObjectData() {
		return isReadyToFetchObjectData;
	}

	@Override
	public void onResume() {
		super.onResume();
		isReadyToFetchObjectData = true;
	}

}
