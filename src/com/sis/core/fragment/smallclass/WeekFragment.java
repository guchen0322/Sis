package com.sis.core.fragment.smallclass;

import java.util.ArrayList;

import org.apache.http.Header;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

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
import com.sis.core.fragment.base.BaseDataFragment;
import com.sis.core.net.SISHttpClient;
import com.sis.core.utils.JsonUtil;
import com.sis.core.utils.TimeUtils;
import com.sis.core.widget.switchView.SwitchButton;

public class WeekFragment extends BaseDataFragment {

	protected FragmentType fragmentType;
	protected int currColor;

	private BarChart weekChart;
	private ArrayList<String> xVals;
	private ArrayList<BarDataSet> dataSets;
	private BarDataSet set1;
	private BarDataSet set2;

	private SwitchButton weekSB;
	private String dataType = "01";

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
		View weekLayout = inflater.inflate(R.layout.fragment_week, container, false);

		weekSB = (SwitchButton) mActivity.findViewById(R.id.dataCompareSB);
		weekSB.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					setSwitchData(true);
				} else {
					setSwitchData(false);
				}
			}
		});

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

		return weekLayout;
	}

	private void getServerData() {
		StringBuffer url = new StringBuffer(Constant.WEEK_URL);
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

	public void setData(ResInfo info) {
		mCallBackListener.dataCallBack(info);

		ArrayList<SYGP> sygps = info.getSygps();
		xVals = new ArrayList<String>();
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		for (int i = sygps.size() - 1; i >= 0; i--) {
			xVals.add(TimeUtils.formatTime(sygps.get(i).getDate(), "MM/dd"));
			yVals1.add(new BarEntry(Math.round(Double.valueOf(sygps.get(i).getValue())), sygps.size() - i - 1));
		}

		ArrayList<SYGP> dbsygps = info.getDbsygps();
		ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
		for (int i = dbsygps.size() - 1; i >= 0; i--) {
			yVals2.add(new BarEntry(Math.round(Double.valueOf(dbsygps.get(i).getValue())), dbsygps.size() - i - 1));
		}

		set1 = new BarDataSet(yVals1, "");
		set1.setColor(currColor);
		set2 = new BarDataSet(yVals2, "");
		set2.setColor(Color.rgb(255, 0, 0));

		dataSets = new ArrayList<BarDataSet>();

		// 默认一组
		setSwitchData(weekSB.isChecked());
	}

	private void setSwitchData(boolean isCompare) {
		dataSets.clear();
		dataSets.add(set1);
		if (isCompare)
			dataSets.add(set2);
		BarData barData = new BarData(xVals, dataSets);

		// add space between the dataset groups in percent of bar-width
		barData.setGroupSpace(110f);
		weekChart.setData(barData);

		weekChart.animateXY(2000, 2000);
		weekChart.invalidate();
	}

	@Override
	public void fetchObjectData() {
		Toast.makeText(mActivity, "Week", Toast.LENGTH_SHORT).show();
		getServerData();
	}

}