package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import org.apache.http.Header;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.sis.core.R;
import com.sis.core.entity.ResInfo;
import com.sis.core.entity.SYGP;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.net.SISHttpClient;
import com.sis.core.utils.JsonUtil;
import com.sis.core.utils.TimeUtils;

public class MinuteFragment extends BaseFragment {

	protected FragmentType fragmentType;
	protected int currColor;

	private LineChart minuteChart;

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
		x.setTextColor(currColor);
		x.setTypeface(Typeface.DEFAULT_BOLD);
		x.setAdjustXLabels(false);
		x.setPosition(XLabelPosition.BOTTOM);

		YLabels y = minuteChart.getYLabels();
		y.setLabelCount(7);
		y.setTextColor(currColor);
		y.setTypeface(Typeface.DEFAULT_BOLD);

		minuteChart.animateXY(2000, 2000);
		// add data
		// setData(10, 100);
		// dont forget to refresh the drawing minuteChart.invalidate();

		// get data from server
		getServerData();

		return minuteLayout;
	}

	private void getServerData() {
		String start = TimeUtils.getStartTime(5);
		String end = TimeUtils.getEndTime(5);
		String url = "http://oa.sygpp.com:8091/home/getdatabyhour?starttime=" + start + "&endtime=" + end + "&type=01";
		Log.d("zhang.h", url);
		SISHttpClient.get(url, new BaseJsonHttpResponseHandler<ResInfo>() {

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ResInfo errorResponse) {

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ResInfo resInfo) {

			}

			@Override
			protected ResInfo parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				Log.d("zhang.h", rawJsonData);
				ResInfo info = JsonUtil.getResInfo(rawJsonData, 1);
				ArrayList<SYGP> data = info.getSygps();
				Log.d("zhang.h", "data size=" + data.size());
				// 给数据集设置X轴时间值
				setData2(data);
				handler.sendEmptyMessage(0);
				return null;
			}
		});
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			minuteChart.invalidate();
		}
	};

	private void setData2(ArrayList<SYGP> data) {
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> vals1 = new ArrayList<Entry>();
		// 倒叙排列
		for (int i = data.size() - 1; i >= 0; i--) {
			xVals.add(TimeUtils.formatTime(data.get(i).getXVALUE()));
			// y轴数据 x坐标点位
			vals1.add(new Entry(Float.parseFloat(data.get(i).getYVALUE()), data.size() - i - 1));
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
	}

}