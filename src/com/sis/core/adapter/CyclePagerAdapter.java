package com.sis.core.adapter;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.sis.core.App;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseDataFragment;
import com.sis.core.fragment.smallclass.DayFragment;
import com.sis.core.fragment.smallclass.MinuteFragment;
import com.sis.core.fragment.smallclass.MonthFragment;
import com.sis.core.fragment.smallclass.SeasonFragment;
import com.sis.core.fragment.smallclass.WeekFragment;
import com.sis.core.fragment.smallclass.YearFragment;
import com.sis.core.listener.DataCallBackListener;

@SuppressLint("UseSparseArrays")
public class CyclePagerAdapter extends FragmentStatePagerAdapter {

	private FragmentType fragmentType;
	private DataCallBackListener mListenter;

	private HashMap<Integer, BaseDataFragment> mFragments;
	private HashMap<Integer, Boolean> mFragmentsState;

	public CyclePagerAdapter(FragmentManager fm, FragmentType fragmentType, DataCallBackListener mCallBackListener) {
		super(fm);
		this.fragmentType = fragmentType;
		this.mListenter = mCallBackListener;

		this.mFragments = App.getInstance().getmFragments();
		this.mFragmentsState = App.getInstance().getmFragmentsState();
	}

	@Override
	public Fragment getItem(int position) {
		BaseDataFragment fragment = null;
		switch (position) {
		case 0:
			fragment = MinuteFragment.newInstance(fragmentType);
			break;
		case 1:
			fragment = DayFragment.newInstance(fragmentType);
			break;
		case 2:
			fragment = WeekFragment.newInstance(fragmentType);
			break;
		case 3:
			fragment = MonthFragment.newInstance(fragmentType);
			break;
		case 4:
			fragment = SeasonFragment.newInstance(fragmentType);
			break;
		case 5:
			fragment = YearFragment.newInstance(fragmentType);
			break;
		}
		fragment.setDataCallBackListener(mListenter);
		mFragments.put(position, fragment);
		mFragmentsState.put(position, false);
		return fragment;
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
		mFragments.remove(position);
		mFragmentsState.remove(position);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
		// current fragment fetch data
		if (mFragmentsState.containsKey(position) && !mFragmentsState.get(position)) {
			BaseDataFragment fragment = mFragments.get(position);
			if (fragment.isReadyToFetchObjectData()) {
				mFragmentsState.put(position, true);
				fragment.fetchObjectData();
			}
		}
	}

}
