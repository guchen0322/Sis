package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.LargeValueFormatter;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;

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

		// disable the drawing of values
		monthChart.setDrawYValues(true);
		monthChart.setValueTextSize(12.0f);

		monthChart.setDrawBorder(true);
		monthChart.setBorderWidth(1);
		monthChart.setBorderPositions(new BorderPosition[] { BorderPosition.LEFT, BorderPosition.BOTTOM });

		monthChart.setDescription("");
		monthChart.setDrawLegend(false);
		monthChart.setDrawBarShadow(false);
		monthChart.setTouchEnabled(false);

		// scaling can now only be done on x- and y-axis separately
		monthChart.setPinchZoom(false);
		monthChart.setValueFormatter(new LargeValueFormatter());

		monthChart.setDrawGridBackground(false);
		monthChart.setDrawHorizontalGrid(false);

		XLabels xl = monthChart.getXLabels();
		xl.setCenterXLabelText(true);
		xl.setTextColor(Color.rgb(0, 188, 13));
		xl.setTypeface(Typeface.DEFAULT_BOLD);
		xl.setAdjustXLabels(false);
		xl.setPosition(XLabelPosition.BOTTOM);

		YLabels yl = monthChart.getYLabels();
		yl.setFormatter(new LargeValueFormatter());
		yl.setTextColor(Color.rgb(0, 188, 13));
		yl.setTypeface(Typeface.DEFAULT_BOLD);

		// add data
		setData(7);

		monthChart.animateXY(2000, 2000);

		// dont forget to refresh the drawing
		monthChart.invalidate();

		return monthLayout;
	}

	public void setData(int count) {
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add((i + 1990) + "");
		}

		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

		float mult = 1000;
		for (int i = 0; i < count; i++) {
			float val = (float) (Math.random() * mult) + 3;
			yVals1.add(new BarEntry(val, i));
		}

		for (int i = 0; i < count; i++) {
			float val = (float) (Math.random() * mult) + 3;
			yVals2.add(new BarEntry(val, i));
		}

		BarDataSet set1 = new BarDataSet(yVals1, "");
		set1.setColor(Color.rgb(0, 188, 13));
		BarDataSet set2 = new BarDataSet(yVals2, "");
		set2.setColor(Color.rgb(255, 0, 0));

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);
		dataSets.add(set2);

		BarData data = new BarData(xVals, dataSets);

		// add space between the dataset groups in percent of bar-width
		data.setGroupSpace(110f);

		monthChart.setData(data);
	}

}