package com.sis.core.adapter;

import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.fragment.smallclass.DayFragment;
import com.sis.core.fragment.smallclass.MinuteFragment;
import com.sis.core.fragment.smallclass.MonthFragment;
import com.sis.core.fragment.smallclass.SeasonFragment;
import com.sis.core.fragment.smallclass.WeekFragment;
import com.sis.core.fragment.smallclass.YearFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CyclePagerAdapter extends FragmentStatePagerAdapter {

	public CyclePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		BaseFragment fragment = null;
		switch (position) {
		case 0:
			fragment = MinuteFragment.newInstance();
			break;
		case 1:
			fragment = DayFragment.newInstance();
			break;
		case 2:
			fragment = WeekFragment.newInstance();
			break;
		case 3:
			fragment = MonthFragment.newInstance();
			break;
		case 4:
			fragment = SeasonFragment.newInstance();
			break;
		case 5:
			fragment = YearFragment.newInstance();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 6;
	}

}
