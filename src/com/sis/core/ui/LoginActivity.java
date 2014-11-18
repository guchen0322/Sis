package com.sis.core.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sis.core.R;
import com.sis.core.ui.base.BaseActivity;

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
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
