package com.sis.core.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sis.core.R;

public class WeekStatisticsFragment extends Fragment {

    static WeekStatisticsFragment newInstance() {
        WeekStatisticsFragment newFragment = new WeekStatisticsFragment();
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekstatistics, container, false);
        return view;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
