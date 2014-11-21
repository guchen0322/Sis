package com.sis.core.adapter;

import com.sis.core.enums.FragmentType;
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

	private FragmentType fragmentType;

	public CyclePagerAdapter(FragmentManager fm, FragmentType fragmentType) {
		super(fm);
		this.fragmentType = fragmentType;
	}

	@Override
	public Fragment getItem(int position) {
		BaseFragment fragment = null;
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
		return fragment;
	}

	@Override
	public int getCount() {
		return 6;
	}

}
