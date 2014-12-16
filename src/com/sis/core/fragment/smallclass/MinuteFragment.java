package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import org.apache.http.Header;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.sis.core.Constant;
import com.sis.core.R;
import com.sis.core.entity.ResInfo;
import com.sis.core.entity.SYGP;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseDataFragment;
import com.sis.core.net.SISHttpClient;
import com.sis.core.utils.JsonUtil;

public class MinuteFragment extends BaseDataFragment {

	protected FragmentType fragmentType;
	protected int currColor;

	private LineChart minuteChart;
	private String dataType = "01";

	public static MinuteFragment newInstance(FragmentType fragmentType) {
		MinuteFragment fragment = new MinuteFragment();
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
		minuteChart.setNoDataText("加载中...");
		// minuteChart.setValueTextColor(Color.rgb(231, 84, 29));

		XLabels x = minuteChart.getXLabels();
		x.setTextColor(currColor);
		x.setTypeface(Typeface.DEFAULT_BOLD);
		x.setAdjustXLabels(false);
		x.setPosition(XLabelPosition.BOTTOM);

		YLabels y = minuteChart.getYLabels();
		y.setLabelCount(7);
		y.setTextColor(currColor);
		y.setTypeface(Typeface.DEFAULT_BOLD);

		return minuteLayout;
	}

	private void getServerData() {
		StringBuffer url = new StringBuffer(Constant.MIN_URL);
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
				minuteChart.setNoDataText("加载数据失败");
				minuteChart.invalidate();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ResInfo resInfo) {
				if (resInfo != null) {
					setData(resInfo);
				}
			}

			@Override
			protected ResInfo parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return JsonUtil.getResInfo(rawJsonData);
			}
		});
	}

	private void setData(ResInfo info) {
		mCallBackListener.dataCallBack(info);

		ArrayList<SYGP> sygps = info.getSygps();
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> vals1 = new ArrayList<Entry>();
		// 倒叙排列
		for (int i = sygps.size() - 1; i >= 0; i--) {
			xVals.add(sygps.get(i).getDate());
			// y轴数据 x坐标点位
			vals1.add(new Entry(Float.valueOf(sygps.get(i).getValue()), sygps.size() - i - 1));
		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(vals1, "");
		set1.setDrawFilled(true);
		set1.setDrawCircles(false);
		set1.setFillAlpha(255);
		set1.setFillColor(currColor);
		set1.setColor(currColor);

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1);

		// create a data object with the datasets
		LineData lineData = new LineData(xVals, dataSets);

		// set data
		minuteChart.setData(lineData);

		minuteChart.animateXY(2000, 2000);
		minuteChart.invalidate();
	}

	@Override
	public void fetchObjectData() {
		Toast.makeText(mActivity, "Minute", Toast.LENGTH_SHORT).show();
		getServerData();
	}

}