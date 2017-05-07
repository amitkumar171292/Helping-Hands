package com.example.helpinghand;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainPage extends Activity implements SensorEventListener {
	SensorManager snsmgr;
	Sensor acc;
	int a = 0;
	ImageButton img;
	TextView t1;
	MediaPlayer ourSong;
	static int cc = 0;
	float string_temp;
	SharedPreferences shared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		setContentView(R.layout.main_page);
		super.onCreate(savedInstanceState);
		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Personal Security");
		th.addTab(specs);

		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Extra Features");
		th.addTab(specs);

		img = (ImageButton) findViewById(R.id.imgbt1);
		snsmgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		acc = snsmgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		t1 = (TextView) findViewById(R.id.tv1);

		ParseAnalytics.trackAppOpened(getIntent());
		PushService.setDefaultPushCallback(this, MainPage.class);
		ParseInstallation.getCurrentInstallation().saveEventually();
		shared = getSharedPreferences("user", MODE_PRIVATE);
		string_temp = shared.getFloat("shaker", 15);

	}

	public void SOS(View v) {
		startActivity(new Intent(MainPage.this, SOS.class));
	}

	public void Scream(View v) {
		cc++;
		if (cc % 2 != 0) {
			ourSong = MediaPlayer.create(MainPage.this, R.raw.ser);
			ourSong.start();
		} else {
			ourSong.stop();
		}
	}
	public void help(View v)
	{
		startActivity(new Intent(MainPage.this, Help.class));

	}

	public void Tips(View v) {
		startActivity(new Intent(MainPage.this, Tips.class));
	}

	public void Flip(View v) {
		startActivity(new Intent(MainPage.this, Rights.class));
	}

	public void shake(View v) {
		startActivity(new Intent(MainPage.this, Shake.class));
	}

	public void TrackIt(View v) {
		startActivity(new Intent(MainPage.this, Tracker.class));
	}

	public void startKaro(View v) {

		snsmgr.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
		img.setImageResource(R.drawable.alert2);
		a++;
		if (a % 2 != 0) {
			Toast.makeText(getBaseContext(), "started", Toast.LENGTH_SHORT)
					.show();
			t1.setText("Application Now Can Sense Shake Please Ensure That Your GPS And Internet Is ON !!!!");

		}

		if (a % 2 == 0) {

			img.setImageResource(R.drawable.alert);
			t1.setText("");
			Toast.makeText(getBaseContext(), "stopped", Toast.LENGTH_SHORT)
					.show();
			snsmgr.unregisterListener(this, acc);
			Intent open = new Intent(getBaseContext(), StartService.class);
			stopService(open);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub

		float[] sensorvalues = arg0.values;
		if (sensorvalues[0] > string_temp) {

			Toast.makeText(getBaseContext(), "application is activated",
					Toast.LENGTH_SHORT).show();
			snsmgr.unregisterListener(this, acc);
			if (a % 2 != 0) {

				Intent open = new Intent(getBaseContext(), StartService.class);
				startService(open);
			}
		}

	}

	public void setContacts(View v) {
		Intent i = new Intent(getBaseContext(), SetContacts.class);
		startActivity(i);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		string_temp = shared.getFloat("shaker", 15);
	}

}
