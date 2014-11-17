package com.sis.core.ui;

import com.sis.core.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	EditText userET, pswdET;
	Button loginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userET = (EditText) findViewById(R.id.login_user);
		pswdET = (EditText) findViewById(R.id.login_pswd);
		loginBtn = (Button) findViewById(R.id.login);
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
		        intent.setClass(LoginActivity.this, MainActivity.class);
		        startActivity(intent);
			}
		});
	}
}
