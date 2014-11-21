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
import com.sis.core.fragment.base.BaseFragment;

public class YearFragment extends BaseFragment {
	protected String[] mParties = new String[] { "Q1季度", "Q2季度", "Q3季度", "Q4季度" };
	private ArrayList<String> legends;

	private PieChart yearChart;

	public static YearFragment newInstance() {
		YearFragment fragment = new YearFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View yearLayout = inflater.inflate(R.layout.fragment_year, container, false);
		yearChart = (PieChart) yearLayout.findViewById(R.id.yearChart);

		// change the color of the center-hole
		yearChart.setHoleColor(Color.rgb(235, 235, 235));
		yearChart.setHoleRadius(60f);

		yearChart.setDescription("");
		yearChart.setDrawYValues(true);
		yearChart.setValueTextSize(15.0f);
		yearChart.setDrawCenterText(false);

		yearChart.setDrawHoleEnabled(false);
		yearChart.setRotationAngle(0);

		// draws the corresponding description value into the slice
		yearChart.setDrawXValues(true);

		// enable rotation of the chart by touch
		yearChart.setRotationEnabled(false);

		// display percentage values
		yearChart.setUsePercentValues(false);

		setData(4, 100);

		yearChart.animateXY(1500, 1500);

		Legend l = yearChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(10f);
		l.setTextSize(12.0f);

		return yearLayout;
	}

	private void setData(int count, float range) {
		legends = new ArrayList<String>();
		legends.add("去年Q1 354.98MW");
		legends.add("去年Q2 354.98MW");
		legends.add("去年Q3 354.98MW");
		legends.add("去年Q4 354.98MW");

		float mult = range;
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		for (int i = 0; i < count; i++) {
			yVals.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
		}

		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++)
			xVals.add(mParties[i % mParties.length]);

		PieDataSet ySet = new PieDataSet(yVals, "");
		ySet.setSliceSpace(3f);

		// add a lot of colors
		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.rgb(34, 165, 224));
		colors.add(Color.rgb(0, 150, 66));
		colors.add(Color.rgb(248, 181, 48));
		colors.add(Color.rgb(231, 84, 29));
		ySet.setColors(colors);

		PieData data = new PieData(xVals, ySet);
		data.setmLegendVals(legends);
		yearChart.setData(data);

		// undo all highlights
		yearChart.highlightValues(null);

		yearChart.invalidate();
	}

}