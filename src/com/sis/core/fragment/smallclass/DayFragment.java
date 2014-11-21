package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.sis.core.R;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseFragment;

public class DayFragment extends BaseFragment {

	protected FragmentType fragmentType;
	protected int currColor;
	
	private LineChart dayChart;

	public static DayFragment newInstance(FragmentType fragmentType) {
		DayFragment fragment = new DayFragment();
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
		View dayLayout = inflater.inflate(R.layout.fragment_day, container, false);

		dayChart = (LineChart) dayLayout.findViewById(R.id.dayChart);

		// if enabled, the chart will always start at zero on the y-axis
		dayChart.setStartAtZero(false);

		// disable the drawing of values into the chart
		dayChart.setDrawYValues(true);
		dayChart.setValueTextSize(12.0f);

		dayChart.setDrawBorder(true);
		dayChart.setBorderWidth(1);
		dayChart.setBorderPositions(new BorderPosition[] { BorderPosition.LEFT, BorderPosition.BOTTOM });

		dayChart.setDrawLegend(false);

		// no description text
		dayChart.setDescription("");

		// enable value highlighting
		dayChart.setHighlightEnabled(true);

		// enable touch gestures
		dayChart.setTouchEnabled(false);

		// enable scaling and dragging
		dayChart.setDragEnabled(true);
		dayChart.setScaleEnabled(true);
		dayChart.setDrawGridBackground(false);

		// if disabled, scaling can be done on x- and y-axis separately
		dayChart.setPinchZoom(true);

		dayChart.setDrawVerticalGrid(false);
		dayChart.setDrawHorizontalGrid(false);

		XLabels xl = dayChart.getXLabels();
		xl.setTextColor(currColor);
		xl.setTypeface(Typeface.DEFAULT_BOLD);
		xl.setAdjustXLabels(false);
		xl.setPosition(XLabelPosition.BOTTOM);

		YLabels yl = dayChart.getYLabels();
		yl.setLabelCount(7);
		yl.setTextColor(currColor);
		yl.setTypeface(Typeface.DEFAULT_BOLD);

		// add data
		setData(10, 100);
		
		dayChart.animateX(2000);

		return dayLayout;
	}

	private void setData(int count, float range) {
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add((i) + "");
		}

		ArrayList<Entry> yVals = new ArrayList<Entry>();

		for (int i = 0; i < count; i++) {
			float mult = (range + 1);
			float val = (float) (Math.random() * mult) + 3;
			yVals.add(new Entry(val, i));
		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(yVals, "");
		set1.setColor(currColor);
		set1.setCircleColor(currColor);
		set1.setLineWidth(1.5f);
		set1.setFillAlpha(65);
		set1.setFillColor(currColor);

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1); // add the datasets

		// create a data object with the datasets
		LineData data = new LineData(xVals, dataSets);

		// set data
		dayChart.setData(data);
	}
}