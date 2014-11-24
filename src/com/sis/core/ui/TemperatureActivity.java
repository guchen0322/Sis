package com.sis.core.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sis.core.R;
import com.sis.core.ui.base.BaseActivity;

public class TemperatureActivity extends BaseActivity implements OnClickListener {

	private ImageView backIV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temperature);

		backIV = (ImageView) findViewById(R.id.backIV);
		backIV.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backIV:
			back();
			break;
		}
	}

}
