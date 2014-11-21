package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.sis.core.R;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseFragment;

public class SeasonFragment extends BaseFragment {

	protected FragmentType fragmentType;
	protected int currColor;
	
	private String[] mParties = new String[] { "07月", "08月", "09月" };
	private ArrayList<String> legends;

	private PieChart seasonChart;

	public static SeasonFragment newInstance(FragmentType fragmentType) {
		SeasonFragment fragment = new SeasonFragment();
		Bundle args = new Bundle();
		args.putSerializable(KEY_FRAGMENT_TYPE, fragmentType);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentType = (FragmentType) getArguments().getSerializable(KEY_FRAGMENT_TYPE);
		switch (fragmentType) {
		case JZFH:
			currColor = JZFH_COLOR;
			break;
		case FDL:
			currColor = FDL_COLOR;
			break;
		case FDMH:
			currColor = FDMH_COLOR;
			break;
		case GDMH:
			currColor = GDMH_COLOR;
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View seasonLayout = inflater.inflate(R.layout.fragment_season, container, false);
		seasonChart = (PieChart) seasonLayout.findViewById(R.id.seasonChart);

		// change the color of the center-hole
		seasonChart.setHoleColor(Color.rgb(235, 235, 235));
		seasonChart.setHoleRadius(60f);

		seasonChart.setDescription("");
		seasonChart.setDrawYValues(true);
		seasonChart.setValueTextSize(15.0f);
		seasonChart.setDrawCenterText(false);

		seasonChart.setDrawHoleEnabled(true);
		seasonChart.setRotationAngle(0);

		// draws the corresponding description value into the slice
		seasonChart.setDrawXValues(true);

		// enable rotation of the chart by touch
		seasonChart.setRotationEnabled(false);

		// display percentage values
		seasonChart.setUsePercentValues(false);

		setData(3, 100);

		seasonChart.animateXY(1500, 1500);

		Legend l = seasonChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(10f);
		l.setTextSize(12.0f);
		
		return seasonLayout;
	}

	private void setData(int count, float range) {
		legends = new ArrayList<String>();
		legends.add("上季度04月  354.98MW");
		legends.add("上季度05月  354.98MW");
		legends.add("上季度06月  354.98MW");

		float mult = range;
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		for (int i = 0; i < count; i++) {
			yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
		}

		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++)
			xVals.add(mParties[i % mParties.length]);

		PieDataSet set1 = new PieDataSet(yVals1, "");
		set1.setSliceSpace(3f);

		// add a lot of colors
		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.rgb(0, 188, 13));
		colors.add(Color.rgb(248, 181, 48));
		colors.add(Color.rgb(231, 84, 29));
		set1.setColors(colors);

		PieData data = new PieData(xVals, set1);
		data.setmLegendVals(legends);
		seasonChart.setData(data);

		// undo all highlights
		seasonChart.highlightValues(null);

		seasonChart.invalidate();
	}
}