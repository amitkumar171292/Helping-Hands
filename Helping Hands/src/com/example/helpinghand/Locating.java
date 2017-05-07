package com.example.helpinghand;

import java.util.HashMap;
import java.util.List;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Locating extends Activity
{
	LocationManager locmgr;
	LocationListener loclis;
	SmsManager smsmgr;
	SharedPreferences sp;
	SharedPreferences.Editor sped;
	String str=" The Location Of Your Contact Is :  ",str1="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Toast.makeText(getBaseContext(), "Activity is started",Toast.LENGTH_SHORT).show();
		sp=getSharedPreferences("CONTACTS",MODE_PRIVATE);
		sped=sp.edit();
		str1=sp.getString("C4",null);
		smsmgr=SmsManager.getDefault();
		locmgr=(LocationManager)getSystemService(LOCATION_SERVICE);
		loclis=new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location loc) 
			{
				Toast.makeText(getBaseContext(),"location changed",Toast.LENGTH_SHORT).show();
				double lati=loc.getLatitude();
				double longi=loc.getLongitude();
				Toast.makeText(getBaseContext(),"Longitude : "+longi+"Latitude : "+lati,Toast.LENGTH_SHORT).show(); 
			
				Geocoder gc=new Geocoder(getBaseContext());
				try {
					List<Address> addr=gc.getFromLocation(lati, longi, 1);
					for(Address ad : addr)
					{
						for (int i = 0; i < ad.getMaxAddressLineIndex(); i++) {
							str+=ad.getAddressLine(i);
							
						}
					}
				        loc=null;
				        }
				catch (Exception e) 
				{
					Toast.makeText(getBaseContext(),"exception :"+e,Toast.LENGTH_SHORT).show();
					
				}



				smsmgr.sendTextMessage(str1,null,str, null, null);
			Toast.makeText(getBaseContext(),"your help  message is sent",Toast.LENGTH_SHORT).show();
		
			str=" ";
			}
		};
		
		locmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,600000,6440000,loclis);

	}
	
	
	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
       locmgr.removeUpdates(loclis);
		super.onDestroy();
	}

}
