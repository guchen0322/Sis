package com.sis.core.ui;

import com.sis.core.App;
import com.sis.core.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class GreenDataActivity extends Activity implements OnClickListener{
	
	private ImageView backIV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_green_data);
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
		
	// 返回
	public void back() {
		App.getActivityManager().popActivity(this);
	}
}
