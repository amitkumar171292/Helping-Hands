package com.example.helpinghand;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class StartService extends Service 
{
	LocationManager locmgr;
	LocationListener loclis;
	SmsManager smsmgr;
	SharedPreferences sp;
	SharedPreferences sp1;
	SharedPreferences.Editor sped;
	String str=" help me i am in danger MY LOCATION : ",str1="",str2="",str3="",str4="Help ",str5="";
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = {  MediaRecorder.OutputFormat.THREE_GPP,MediaRecorder.OutputFormat.MPEG_4 };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_3GP, AUDIO_RECORDER_FILE_EXT_MP4 };
    
	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		
		Toast.makeText(getBaseContext(), "Service is start",Toast.LENGTH_SHORT).show();
		
		sp=getSharedPreferences("CONTACTS",MODE_PRIVATE);
		sp1=getSharedPreferences("user",MODE_PRIVATE);
		str5=sp1.getString("user", null);
		str4+=str5+":";
		sped=sp.edit();
		str1=sp.getString("C1",null);
		str2=sp.getString("C2",null);
		str3=sp.getString("C3",null);
		smsmgr=SmsManager.getDefault();
		startRecording();
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
							str4+=ad.getAddressLine(i);
							
						}
					}
				        loc=null;
				        }
				catch (Exception e) 
				{
					Toast.makeText(getBaseContext(),"exception :"+e,Toast.LENGTH_SHORT).show();
					
				}

				HashMap<String, Object> dict = new HashMap<String, Object>();
				dict.put("address",str4);
						 ParseCloud.callFunctionInBackground(
						        "Ticket",
						         dict,
						         new FunctionCallback<Object>()
						    {
						    @Override
							public void done(Object object, ParseException e) 
						    {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(), "notified",Toast.LENGTH_SHORT).show();
							}
						    });



				smsmgr.sendTextMessage(str1,null,str, null, null);
				smsmgr.sendTextMessage(str2,null,str, null, null);
				smsmgr.sendTextMessage(str3,null,str, null, null);
			Toast.makeText(getBaseContext(),"your help  message is sent",Toast.LENGTH_SHORT).show();
			str=" ";
			}
		};
		locmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,300000,0,loclis);
		return START_STICKY;
		
		
	}
	
	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		stopRecording();
       locmgr.removeUpdates(loclis);
		super.onDestroy();
	}
	
	private void startRecording(){
		
		
		recorder = new MediaRecorder();
	       
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
       
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);
       
        try {
                recorder.prepare();
                recorder.start();
        } catch (IllegalStateException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
		
		
	} 
	
	
	private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);
       
        if(!file.exists()){
                file.mkdirs();
        }
       
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
}
	
	
	
	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
               
        }
};

private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
               
        }
};
	


private void stopRecording(){
    if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();
           
            recorder = null;
    }
}
	
	

}
