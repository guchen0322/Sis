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

public class SeasonFragment extends BaseFragment {

	protected String[] mParties = new String[] { "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
			"Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S",
			"Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z" };

	private PieChart seasonChart;

	public static SeasonFragment newInstance() {
		SeasonFragment fragment = new SeasonFragment();
		return fragment;
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
		seasonChart.setDrawCenterText(false);

		seasonChart.setDrawHoleEnabled(true);

		seasonChart.setRotationAngle(0);

		// draws the corresponding description value into the slice
		seasonChart.setDrawXValues(true);

		// enable rotation of the chart by touch
		seasonChart.setRotationEnabled(true);

		// display percentage values
		seasonChart.setUsePercentValues(false);
		// seasonChart.setDrawUnitsInChart(true);

		seasonChart.setCenterText("MPAndroidChart\nLibrary");

		setData(3, 100);

		seasonChart.animateXY(1500, 1500);
		// seasonChart.spin(2000, 0, 360);

		Legend l = seasonChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(5f);

		return seasonLayout;
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
		seasonChart.setData(data);

		// undo all highlights
		seasonChart.highlightValues(null);

		seasonChart.invalidate();
	}
}