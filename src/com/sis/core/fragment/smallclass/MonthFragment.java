package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.YLabels;
import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.widget.MyMarkerView;

public class MonthFragment extends BaseFragment {
	
	private BarChart monthChart;

	public static MonthFragment newInstance() {
		MonthFragment fragment = new MonthFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View monthLayout = inflater.inflate(R.layout.fragment_month, container, false);
		monthChart = (BarChart) monthLayout.findViewById(R.id.monthChart);

		monthChart.setDescription("");

		// enable touch gestures
		monthChart.setTouchEnabled(false);

		// disable the drawing of values
		monthChart.setDrawYValues(false);

		// scaling can now only be done on x- and y-axis separately
		monthChart.setPinchZoom(false);
		monthChart.setValueFormatter(new LargeValueFormatter());

		monthChart.setDrawBarShadow(false);

		monthChart.setDrawGridBackground(false);
		monthChart.setDrawHorizontalGrid(false);

		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

		// define an offset to change the original position of the marker
		// (optional)
		mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

		// set the marker to the chart
		monthChart.setMarkerView(mv);

		YLabels yl = monthChart.getYLabels();
		yl.setFormatter(new LargeValueFormatter());

		setData(10);

		monthChart.animateXY(2000, 2000);

		// dont forget to refresh the drawing
		monthChart.invalidate();

		return monthLayout;
	}

	public void setData(int progress) {

		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < progress; i++) {
			xVals.add((i + 1990) + "");
		}

		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

		float mult = progress * 10000000f;

		for (int i = 0; i < progress; i++) {
			float val = (float) (Math.random() * mult) + 3;
			yVals1.add(new BarEntry(val, i));
		}

		for (int i = 0; i < progress; i++) {
			float val = (float) (Math.random() * mult) + 3;
			yVals2.add(new BarEntry(val, i));
		}

		// create 3 datasets with different types
		BarDataSet set1 = new BarDataSet(yVals1, "Company A");
		// set1.setColors(ColorTemplate.createColors(getApplicationContext(), ColorTemplate.FRESH_COLORS));
		set1.setColor(Color.rgb(104, 241, 175));
		BarDataSet set2 = new BarDataSet(yVals2, "Company B");
		set2.setColor(Color.rgb(164, 228, 251));

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);
		dataSets.add(set2);

		BarData data = new BarData(xVals, dataSets);

		// add space between the dataset groups in percent of bar-width
		data.setGroupSpace(110f);

		monthChart.setData(data);
	}

}