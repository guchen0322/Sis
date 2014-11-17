package com.sis.core.ui;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sis.core.R;

public class TimeStatisticsFragment extends Fragment {

	private Button mAdd;
	private EditText mValue;
	private View mView;
	private GraphicalView mChartView;
	private CategorySeries mSeries = new CategorySeries("");
	private DefaultRenderer mRenderer = new DefaultRenderer();
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.MAGENTA, Color.CYAN };

	static TimeStatisticsFragment newInstance() {
		TimeStatisticsFragment newFragment = new TimeStatisticsFragment();
		return newFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("mytest", "=========TimeStatisticsFragment onCreate========");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("mytest", "=========TimeStatisticsFragment onCreateView========");
		View view = inflater.inflate(R.layout.fragment_timestatistics,
				container, false);
		mView = view;
		mValue = (EditText) view.findViewById(R.id.xValue);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(180);
		mRenderer.setDisplayValues(true);

		mAdd = (Button) view.findViewById(R.id.add);
		mAdd.setEnabled(true);
		mValue.setEnabled(true);

		mAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				double value = 0;
				try {
					value = Double.parseDouble(mValue.getText().toString());
				} catch (NumberFormatException e) {
					mValue.requestFocus();
					return;
				}
				mValue.setText("");
				mValue.requestFocus();
				mSeries.add("Series " + (mSeries.getItemCount() + 1), value);
				SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
				renderer.setColor(COLORS[(mSeries.getItemCount() - 1)
						% COLORS.length]);
				mRenderer.addSeriesRenderer(renderer);
				mChartView.repaint();
			}
		});
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) mView.findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(getActivity(), mSeries,
					mRenderer);
			Log.d("mytest", mChartView == null ? "null" : mChartView.toString());
			mRenderer.setClickEnabled(true);
			mChartView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(getActivity(),
								"No chart element selected", Toast.LENGTH_SHORT)
								.show();
					} else {
						for (int i = 0; i < mSeries.getItemCount(); i++) {
							mRenderer.getSeriesRendererAt(i).setHighlighted(
									i == seriesSelection.getPointIndex());
						}
						mChartView.repaint();
						Toast.makeText(
								getActivity(),
								"Chart data point index "
										+ seriesSelection.getPointIndex()
										+ " selected" + " point value="
										+ seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else {
			mChartView.repaint();
		}
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
