package com.example.helpinghand;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Shake extends Activity implements SensorEventListener {
	float c=0,d=0;
	String str="";
	
	SharedPreferences sp;
	SharedPreferences.Editor sped;
		SensorManager snsmgr;
	Sensor acc;
		EditText e;
		Button b1,b2,b3,b4;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.shake);
			e=(EditText)findViewById(R.id.ed1);
			b3=(Button)findViewById(R.id.set);
			b4=(Button)findViewById(R.id.stop);
			snsmgr=(SensorManager)getSystemService(SENSOR_SERVICE);
			acc=snsmgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sp=getSharedPreferences("user",MODE_PRIVATE);
			sped=sp.edit();
			
			}
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onSensorChanged(SensorEvent arg0) {
			// TODO Auto-generated method stub
			float[] sensorvalues=arg0.values;
			
			if(sensorvalues[0]>c)
			{
				 
				Toast.makeText(getBaseContext(),"SHAKE DETECTED", Toast.LENGTH_SHORT).show();
				 
			}
			
		}
		
		public void setKaro(View v)
		{
			
			Toast.makeText(getBaseContext(),"SENSOR IS ACTIVE", Toast.LENGTH_SHORT).show();
			snsmgr.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
			str=e.getText().toString();
			c=Float.parseFloat(str);
		}
		public void saveKaro(View v)
		{
			Toast.makeText(getBaseContext(),"SENSOR IS DEACTIVATED", Toast.LENGTH_SHORT).show();
			snsmgr.unregisterListener(this, acc);
			sped.putFloat("shaker",c);
			 sped.commit();
		}

	}
