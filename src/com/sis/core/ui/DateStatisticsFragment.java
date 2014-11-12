package com.sis.core.ui;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.sis.core.R;

public class DateStatisticsFragment extends Fragment {
	
	private View mView;
	private GraphicalView mChartView;
	private CategorySeries mSeries = new CategorySeries("");
	private DefaultRenderer mRenderer = new DefaultRenderer();
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
	
    static DateStatisticsFragment newInstance() {
        DateStatisticsFragment newFragment = new DateStatisticsFragment();
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datestatistics, container, false);
        mView = view;
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(180);
        mRenderer.setDisplayValues(true);
        return view;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	LinearLayout layout = (LinearLayout) mView.findViewById(R.id.chart);
        mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
        layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        mSeries.add("Series1", 30);
        mSeries.add("Series2", 70);
        mSeries.add("Series3", 110);
        SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
        renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
        mRenderer.addSeriesRenderer(renderer);
        mChartView.repaint();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
