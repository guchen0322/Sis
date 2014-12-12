package com.sis.core.fragment.smallclass;

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.Header;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.sis.core.Constant;
import com.sis.core.R;
import com.sis.core.entity.ResInfo;
import com.sis.core.entity.SYGP;
import com.sis.core.enums.FragmentType;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.net.SISHttpClient;
import com.sis.core.utils.JsonUtil;
import com.sis.core.utils.TimeUtils;

public class WeekFragment extends BaseFragment {

	protected FragmentType fragmentType;
	protected int currColor;

	private BarChart weekChart;

	public static WeekFragment newInstance(FragmentType fragmentType) {
		WeekFragment fragment = new WeekFragment();
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
		View weekLayout = inflater.inflate(R.layout.fragment_week, container, false);

		weekChart = (BarChart) weekLayout.findViewById(R.id.weekChart);

		// disable the drawing of values
		weekChart.setDrawYValues(true);
		weekChart.setValueTextSize(12.0f);

		weekChart.setDrawBorder(true);
		weekChart.setBorderWidth(1);
		weekChart.setBorderPositions(new BorderPosition[] { BorderPosition.LEFT, BorderPosition.BOTTOM });

		weekChart.setDescription("");
		weekChart.setDrawLegend(false);
		weekChart.setDrawBarShadow(false);
		weekChart.setTouchEnabled(false);

		// scaling can now only be done on x- and y-axis separately
		weekChart.setPinchZoom(false);
		weekChart.setValueFormatter(new LargeValueFormatter());

		weekChart.setDrawGridBackground(false);
		weekChart.setDrawHorizontalGrid(false);

		XLabels xl = weekChart.getXLabels();
		xl.setCenterXLabelText(true);
		xl.setTextColor(currColor);
		xl.setTypeface(Typeface.DEFAULT_BOLD);
		xl.setAdjustXLabels(false);
		xl.setPosition(XLabelPosition.BOTTOM);

		YLabels yl = weekChart.getYLabels();
		yl.setFormatter(new LargeValueFormatter());
		yl.setTextColor(currColor);
		yl.setTypeface(Typeface.DEFAULT_BOLD);

		// add data
		// setData(7);

		weekChart.animateXY(2000, 2000);

		// dont forget to refresh the drawing
		// weekChart.invalidate();

		getServerData();

		return weekLayout;
	}

	private void getServerData() {
		Date now = new Date();
		String startTime = TimeUtils.getWeekEndTime(now);
		String endTime = TimeUtils.getWeekStartTime(now);
		String url = Constant.WEEK_URL + "?starttime=" + startTime + "&endtime=" + endTime + "&type=01";
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
				ResInfo info = JsonUtil.getResInfo(rawJsonData, 1);
				ArrayList<SYGP> data = info.getSygps();
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
			weekChart.invalidate();
		}
	};

	public void setData2(ArrayList<SYGP> data) {
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		for (int i = data.size() - 1; i >= 0; i--) {
			xVals.add(TimeUtils.formatTime(data.get(i).getXVALUE(), "MM/dd"));
			// y轴数据 x坐标点位
			yVals1.add(new BarEntry(Math.round(Double.valueOf(data.get(i).getYVALUE())), data.size() - i - 1));
		}

		BarDataSet set1 = new BarDataSet(yVals1, "");
		set1.setColor(currColor);

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);

		BarData barData = new BarData(xVals, dataSets);

		// add space between the dataset groups in percent of bar-width
		barData.setGroupSpace(110f);
		weekChart.setData(barData);
	}

	public void setData(int count) {
		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < count; i++) {
			xVals.add((i + 1990) + "");
		}

		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

		float mult = 100;
		for (int i = 0; i < count; i++) {
			float val = (float) (Math.random() * mult) + 3;
			yVals1.add(new BarEntry(val, i));
		}

		for (int i = 0; i < count; i++) {
			float val = (float) (Math.random() * mult) + 3;
			yVals2.add(new BarEntry(val, i));
		}

		BarDataSet set1 = new BarDataSet(yVals1, "");
		set1.setColor(currColor);
		BarDataSet set2 = new BarDataSet(yVals2, "");
		set2.setColor(Color.rgb(255, 0, 0));

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);
		dataSets.add(set2);

		BarData data = new BarData(xVals, dataSets);

		// add space between the dataset groups in percent of bar-width
		data.setGroupSpace(110f);

		weekChart.setData(data);
	}

}