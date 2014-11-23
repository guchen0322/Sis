package com.sis.core.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.ui.DataStatisticsActivity;
import com.sis.core.ui.GreenDataActivity;
import com.sis.core.ui.IndexListActivity;

public class DataStatisticsTabFragment extends BaseFragment implements OnClickListener {

	private ImageView dsIV;
	private ImageView ilIV;
	private ImageView gdIV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View dsLayout = inflater.inflate(R.layout.fragment_date_statistics, container, false);

		dsIV = (ImageView) dsLayout.findViewById(R.id.dsIV);
		ilIV = (ImageView) dsLayout.findViewById(R.id.ilIV);
		gdIV = (ImageView) dsLayout.findViewById(R.id.gdIV);
		dsIV.setOnClickListener(this);
		ilIV.setOnClickListener(this);
		gdIV.setOnClickListener(this);

		return dsLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dsIV:
			forwardActivity(DataStatisticsActivity.class);
			break;
		case R.id.ilIV:
			forwardActivity(IndexListActivity.class);
			break;
		case R.id.gdIV:
			forwardActivity(GreenDataActivity.class);
			break;
		}
	}

}