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
				String userName = userET.getText() != null? userET.getText().toString().trim():"";
				String pswd = pswdET.getText() != null? pswdET.getText().toString().trim():"";
				Map<String,String> accounts = App.getAccounts();
				String userkey = null;
				for(String key : accounts.keySet()){
					if(key.equals(userName)){
						userkey = key;
						break;
					}
				}
				if(userkey != null){
					if(accounts.get(userkey).equals(pswd)){
						App.getPreferenceUtils().savePreferenceInt(PreferenceUtils.KEY_LOGIN_STATUS, 1);
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						
						// 退出栈
						App.getActivityManager().popActivity(LoginActivity.this);
					}
				}else
					Toast.makeText(LoginActivity.this, getString(R.string.usernamepswderror), Toast.LENGTH_SHORT).show();;
				
/*					new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							HttpClient client=new DefaultHttpClient();  
							//创建一个POST请求  
							HttpPost httpPost=new HttpPost("http://oa.sygpp.com/SACSIS/HOUR/SACSIS/getallsacsis");  
							//组装数据放到HttpEntity中发送到服务器  
							final List dataList = new ArrayList();  
							dataList.add(new BasicNameValuePair("type", "1"));  
							dataList.add(new BasicNameValuePair("startTime", "2014-09-26 09:00:00"));  
							dataList.add(new BasicNameValuePair("endTime", "2014-09-26 10:00:01"));  
							HttpEntity entity = new UrlEncodedFormEntity(dataList, "UTF-8");
							httpPost.setEntity(entity);  
							//向服务器发送POST请求并获取服务器返回的结果，可能是增加成功返回商品ID，或者失败等信息  
							HttpResponse response=client.execute(httpPost);
							InputStream is = response.getEntity().getContent();
							byte[] buf = new byte[1024];
							int len = 0;
							StringBuilder sb = new StringBuilder();
							while((len = is.read(buf)) != -1){
								sb.append(new String(buf,0,len));
							}
							Log.d("Login", sb.toString());
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					}
				}).start();
				*/
			}
		});
	}
}
