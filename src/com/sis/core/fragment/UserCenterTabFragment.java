package com.sis.core.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sis.core.App;
import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.ui.LoginActivity;
import com.sis.core.utils.PreferenceUtils;

public class UserCenterTabFragment extends BaseFragment {

	private TextView clearDataTV;
	private ImageView mExitIV;
	private TextView userTV;
	private ProgressBar pb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ucLayout = inflater.inflate(R.layout.fragment_user_center, container, false);

		pb = (ProgressBar) ucLayout.findViewById(R.id.pb);
		clearDataTV = (TextView) ucLayout.findViewById(R.id.clearDataTV);
		clearDataTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				// 利用handler延迟发送更改状态信息
				handler.sendEmptyMessageDelayed(0, 2000);
			}
		});
		userTV = (TextView) ucLayout.findViewById(R.id.login_account);
		userTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.KEY_USER_NAME));
		mExitIV = (ImageView) ucLayout.findViewById(R.id.exit);
		mExitIV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				App.getPreferenceUtils().savePreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS, 0);

				forwardActivity(LoginActivity.class);
				App.getActivityManager().popActivity(getActivity());
			}
		});
		return ucLayout;
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pb.setVisibility(View.GONE);
			Toast.makeText(mActivity, "数据已清除", Toast.LENGTH_SHORT).show();
		}
	};

}