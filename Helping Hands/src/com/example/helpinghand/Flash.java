package com.example.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Flash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					SharedPreferences shared = getSharedPreferences("user",MODE_PRIVATE);
					String string_temp = shared.getString("key5","pepsi");
					if(string_temp.equals("value5"))
					{
						Intent b=new Intent("com.example.helpinghands.LOGIN");
						
						startActivity(b);
					}
					else{
					Intent openMain = new Intent(
							"com.example.helpinghands.SIGN");
					startActivity(openMain);}
				}
			}
		};
		timer.start();
	}

	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}

