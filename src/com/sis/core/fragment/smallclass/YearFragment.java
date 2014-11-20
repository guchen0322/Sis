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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;

public class YearFragment extends BaseFragment {

	protected String[] mParties = new String[] { "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
			"Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S",
			"Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z" };

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
		yearChart.setDrawCenterText(false);

		yearChart.setDrawHoleEnabled(false);

		yearChart.setRotationAngle(0);

		// draws the corresponding description value into the slice
		yearChart.setDrawXValues(true);

		// enable rotation of the chart by touch
		yearChart.setRotationEnabled(true);

		// display percentage values
		yearChart.setUsePercentValues(false);
		// yearChart.setDrawUnitsInChart(true);

		setData(4, 100);

		yearChart.animateXY(1500, 1500);
		// yearChart.spin(2000, 0, 360);

		Legend l = yearChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(5f);

		return yearLayout;
	}

	private void setData(int count, float range) {
		float mult = range;

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		// IMPORTANT: In a PieChart, no values (Entry) should have the same
		// xIndex (even if from different DataSets), since no values can be
		// drawn above each other.
		for (int i = 0; i < count; i++) {
			yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
		}

		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < count; i++)
			xVals.add(mParties[i % mParties.length]);

		PieDataSet set1 = new PieDataSet(yVals1, "Election Results");
		set1.setSliceSpace(3f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());

		set1.setColors(colors);

		PieData data = new PieData(xVals, set1);
		yearChart.setData(data);

		// undo all highlights
		yearChart.highlightValues(null);

		yearChart.invalidate();
	}

}