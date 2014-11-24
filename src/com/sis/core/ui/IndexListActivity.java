package com.sis.core.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sis.core.R;
import com.sis.core.ui.base.BaseActivity;

public class IndexListActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private int imgId[] = { R.drawable.index_list_01, R.drawable.index_list_02, R.drawable.index_list_03, R.drawable.index_list_04,
			R.drawable.index_list_05, R.drawable.index_list_06, R.drawable.index_list_07, R.drawable.index_list_08 };

	private ImageView backIV;
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index_list);

		backIV = (ImageView) findViewById(R.id.backIV);
		backIV.setOnClickListener(this);

		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridview.setOnItemClickListener(this);
		gridview.setAdapter(new MyAdapter());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backIV:
			back();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) {
		switch (position) {
		case 0:
			Intent intent = new Intent(this, TemperatureActivity.class);
			startActivity(intent);
			break;
		}
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return imgId.length;
		}

		@Override
		public Object getItem(int position) {
			return imgId[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(IndexListActivity.this).inflate(R.layout.gridview_item, null);

				holder = new Holder();
				holder.itemIV = (ImageView) convertView.findViewById(R.id.itemIV);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.itemIV.setImageResource(imgId[position]);
			return convertView;
		}

	}

	class Holder {
		ImageView itemIV;
	}

}
