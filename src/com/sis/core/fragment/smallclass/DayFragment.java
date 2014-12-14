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

import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
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

public class DayFragment extends BaseDataFragment {

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

		return dayLayout;
	}

	private void getServerData() {
		String url = Constant.DAY_URL + "?type=SYGP:01.SC0001";
		Log.d("zhang.h", url);
		SISHttpClient.get(url, new BaseJsonHttpResponseHandler<ResInfo>() {

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ResInfo errorResponse) {

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

		ArrayList<SYGP> data = info.getSygps();
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> vals1 = new ArrayList<Entry>();
		// 倒叙排列
		for (int i = data.size() - 1; i >= 0; i--) {
			xVals.add(data.get(i).getDate());
			// y轴数据 x坐标点位
			vals1.add(new Entry(Float.parseFloat(data.get(i).getValue()), data.size() - i - 1));
		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(vals1, "");
		set1.setColor(currColor);
		set1.setCircleColor(currColor);
		set1.setLineWidth(1.5f);
		set1.setFillAlpha(65);
		set1.setFillColor(currColor);

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1);

		// create a data object with the datasets
		LineData lineData = new LineData(xVals, dataSets);

		// set data
		dayChart.setData(lineData);

		dayChart.animateX(2000);
		dayChart.invalidate();
	}

	@Override
	public void fetchObjectData() {
		Toast.makeText(mActivity, "Day", Toast.LENGTH_SHORT).show();
		getServerData();
	}
}