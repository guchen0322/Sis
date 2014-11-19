package com.sis.core.listener;

import com.sis.core.enums.FragmentType;

public interface CyclePageChangeListener {
	void cycleChange(FragmentType fragmentType, int position);
}
