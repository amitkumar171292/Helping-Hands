package com.example.helpinghand;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

public class Tips extends Activity implements OnClickListener{
	
	ViewFlipper flippy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tips);
	flippy = (ViewFlipper) findViewById(R.id.viewFlipper1);
	flippy.setOnClickListener(this);
	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	public void Next (View v)
	{
		flippy.showNext();
	}
	public void Previous(View v)
	{
		flippy.showPrevious();
	}
}
