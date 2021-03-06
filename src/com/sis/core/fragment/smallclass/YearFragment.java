package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import org.apache.http.Header;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.sis.core.Constant;
import com.sis.core.R;
import com.sis.core.entity.ResInfo;
import com.sis.core.entity.SYGP;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseDataFragment;
import com.sis.core.net.SISHttpClient;
import com.sis.core.utils.JsonUtil;

public class YearFragment extends BaseDataFragment {

	protected FragmentType fragmentType;
	protected int currColor;
	private ArrayList<String> legends;

	private PieChart yearChart;
	private PieData data;
	private String dataType = "01";
	private SparseArray<String> legendMap = new SparseArray<String>();

	public static YearFragment newInstance(FragmentType fragmentType) {
		YearFragment fragment = new YearFragment();
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
			dataType = "01";
			break;
		case FDL:
			currColor = FDL_COLOR;
			dataType = "02";
			break;
		case FDMH:
			currColor = FDMH_COLOR;
			dataType = "03";
			break;
		case GDMH:
			currColor = GDMH_COLOR;
			dataType = "05";
			break;
		default:
			break;
		}
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
		yearChart.setNoDataText("加载中...");
		yearChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

			@Override
			public void onValueSelected(Entry e, int dataSetIndex) {
				legends.clear();
				legends.add(legendMap.get(e.getXIndex()));
				showChart();
			}

			@Override
			public void onNothingSelected() {

			}
		});

		return yearLayout;
	}

	private void getServerData() {
		StringBuffer url = new StringBuffer(Constant.YEAR_URL);
		String jz = currJZTV.getText().toString();
		if ("1号机组".equals(jz)) {
			url.append("?type=SYGP:").append("01").append(".SC00").append(dataType);
		} else {
			url.append("?type=SYGP:").append("02").append(".SC00").append(dataType);
		}
		Log.d("zhang.h", url.toString());
		SISHttpClient.get(url.toString(), new BaseJsonHttpResponseHandler<ResInfo>() {

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ResInfo errorResponse) {
				yearChart.setNoDataText("加载数据失败");
				yearChart.invalidate();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ResInfo resInfo) {
				if (resInfo != null) {
					setData(resInfo);
				}
			}

			@Override
			protected ResInfo parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return JsonUtil.getYearResInfo(rawJsonData);
			}
		});
	}

	private void setData(ResInfo info) {
		mCallBackListener.dataCallBack(info);

		ArrayList<SYGP> sygps = info.getSygps();
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals = new ArrayList<Entry>();

		ArrayList<SYGP> dbsygps = info.getDbsygps();
		legends = new ArrayList<String>();

		for (int i = 0; i < sygps.size(); i++) {
			SYGP sygp = sygps.get(i);
			SYGP dbsygp = dbsygps.get(i);
			xVals.add(sygp.getDate());
			yVals.add(new Entry(Float.valueOf(sygp.getValue()), i));
			legendMap.put(i, dbsygp.getDate() + "  " + dbsygp.getValue() + "MW");
		}

		PieDataSet ySet = new PieDataSet(yVals, "");
		ySet.setSliceSpace(3f);
		ySet.setColors(info.getColors());

		data = new PieData(xVals, ySet);
		data.setmLegendVals(legends);
		yearChart.setData(data);

		// undo all highlights
		yearChart.highlightValues(null);
		yearChart.invalidate();
		yearChart.animateXY(1500, 1500);

		Legend l = yearChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(10f);
		l.setTextSize(12.0f);
	}

	private void showChart() {
		data.setmLegendVals(legends);
		yearChart.setData(data);

		yearChart.invalidate();
	}

	@Override
	public void fetchObjectData() {
		Toast.makeText(mActivity, "Year", Toast.LENGTH_SHORT).show();
		getServerData();
	}

}