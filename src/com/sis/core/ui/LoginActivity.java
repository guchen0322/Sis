package com.sis.core.ui;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sis.core.App;
import com.sis.core.R;
import com.sis.core.ui.base.BaseActivity;
import com.sis.core.utils.PreferenceUtils;

public class LoginActivity extends BaseActivity {

	EditText userET, pswdET;
	Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userET = (EditText) findViewById(R.id.login_user);
		pswdET = (EditText) findViewById(R.id.login_pswd);
		loginBtn = (Button) findViewById(R.id.loginBtn);

		loginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String userName = userET.getText() != null ? userET.getText().toString().trim() : "";
				String pswd = pswdET.getText() != null ? pswdET.getText().toString().trim() : "";
				Map<String, String> accounts = App.getAccounts();
				String userkey = null;
				for (String key : accounts.keySet()) {
					if (key.equals(userName)) {
						userkey = key;
						break;
					}
				}
				if (userkey != null) {
					if (accounts.get(userkey).equals(pswd)) {
						App.getPreferenceUtils().savePreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS, 1);
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);

						// 退出栈
						App.getActivityManager().popActivity(LoginActivity.this);
					}
				} else {
					Toast.makeText(LoginActivity.this, getString(R.string.usernamepswderror), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
