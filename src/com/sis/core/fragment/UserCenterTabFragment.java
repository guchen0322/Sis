package com.sis.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sis.core.App;
import com.sis.core.R;
import com.sis.core.fragment.base.BaseFragment;
import com.sis.core.ui.LoginActivity;
import com.sis.core.utils.PreferenceUtils;

public class UserCenterTabFragment extends BaseFragment {
	
	ImageView mExitIV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ucLayout = inflater.inflate(R.layout.fragment_user_center, container, false);
		
		mExitIV = (ImageView) ucLayout.findViewById(R.id.exit);
		mExitIV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 App.getPreferenceUtils().savePreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS, 0);
				 Intent intent = new Intent(getActivity(), LoginActivity.class);
				 startActivity(intent);
			}
		});
		return ucLayout;
	}

}