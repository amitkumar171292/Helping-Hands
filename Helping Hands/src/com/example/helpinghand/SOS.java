package com.example.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class SOS extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos);
	}
	public void Ambulance(View v)
	{
		Intent call=new Intent(Intent.ACTION_CALL);
		call.setData(Uri.parse("tel: "+102));	
		call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(call);
	}
	public void Police(View v)
	{
		Intent call=new Intent(Intent.ACTION_CALL);
		call.setData(Uri.parse("tel: "+100));	
		call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(call);
	}
	public void Fire(View v)
	{
		Intent call=new Intent(Intent.ACTION_CALL);
		call.setData(Uri.parse("tel: "+101));	
		call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(call);
	}
	public void US(View v)
	{
		Intent call=new Intent(Intent.ACTION_CALL);
		call.setData(Uri.parse("tel: "+911));	
		call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(call);
	}
	public void Home_Screen(View v)
	{
		startActivity(new Intent(SOS.this, MainPage.class));
	}
}
