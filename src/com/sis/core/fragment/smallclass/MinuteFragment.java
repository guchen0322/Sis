package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;

public class MinuteFragment extends BaseFragment {

	private LineChart minuteChart;

	public static MinuteFragment newInstance() {
		MinuteFragment fragment = new MinuteFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View minuteLayout = inflater.inflate(R.layout.fragment_minute, container, false);

		minuteChart = (LineChart) minuteLayout.findViewById(R.id.minuteChart);
		// if enabled, the chart will always start at zero on the y-axis
		minuteChart.setStartAtZero(false);

		// disable the drawing of values into the chart
		minuteChart.setDrawYValues(true);
		minuteChart.setValueTextSize(12.0f);

		minuteChart.setDrawBorder(false);
		minuteChart.setDrawLegend(false);

		// no description text
		minuteChart.setDescription("");

		// enable value highlighting
		minuteChart.setHighlightEnabled(true);

		// enable touch gestures
		minuteChart.setTouchEnabled(false);

		// enable scaling and dragging
		minuteChart.setDragEnabled(true);
		minuteChart.setScaleEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		minuteChart.setPinchZoom(false);

		minuteChart.setDrawGridBackground(false);
		minuteChart.setDrawVerticalGrid(false);

		XLabels x = minuteChart.getXLabels();
		x.setTextColor(Color.rgb(0, 188, 13));
		x.setTypeface(Typeface.DEFAULT_BOLD);
		x.setAdjustXLabels(false);
		x.setPosition(XLabelPosition.BOTTOM);

		YLabels y = minuteChart.getYLabels();
		y.setLabelCount(7);
		y.setTextColor(Color.rgb(0, 188, 13));
		y.setTypeface(Typeface.DEFAULT_BOLD);

		// add data
		setData(10, 100);

		minuteChart.animateXY(2000, 2000);

		// dont forget to refresh the drawing
		minuteChart.invalidate();

		return minuteLayout;
	}

	private void setData(int count, float range) {
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add((1990 + i) + "");
		}

		ArrayList<Entry> vals1 = new ArrayList<Entry>();
		for (int i = 0; i < count; i++) {
			float mult = (range + 1);
			float val = (float) (Math.random() * mult) + 20;
			vals1.add(new Entry(val, i));
		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(vals1, "");
		set1.setDrawFilled(true);
		set1.setDrawCircles(false);
		set1.setFillAlpha(255);
		set1.setFillColor(Color.rgb(0, 188, 13));
		set1.setColor(Color.rgb(0, 188, 13));

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1);

		// create a data object with the datasets
		LineData data = new LineData(xVals, dataSets);

		// set data
		minuteChart.setData(data);
	}
}