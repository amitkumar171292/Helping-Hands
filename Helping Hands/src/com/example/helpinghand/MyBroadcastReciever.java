package com.example.helpinghand;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyBroadcastReciever extends BroadcastReceiver 
{
	SharedPreferences sp;
	public String phno="",str="",str1="",str2="";
	SharedPreferences.Editor sped;
	@Override
	public void onReceive(Context ctx, Intent arg1)
	{
		// TODO Auto-generated method stub
		//Toast.makeText(ctx, "inside",Toast.LENGTH_SHORT).show();
		sp=ctx.getSharedPreferences("CONTACTS",ctx.MODE_PRIVATE);
		sped=sp.edit();
		str+=sp.getString("C1",null);
		str1+=sp.getString("C2",null);
		str2+=sp.getString("C3",null);
		Bundle b=arg1.getExtras();
		SmsMessage []msgs=null;
		String str4="";
		if(b!=null)
		{
			Object []pdus=(Object [])b.get("pdus");
			msgs=new SmsMessage[pdus.length];
			for(int i=0;i<msgs.length;i++)
			{
				msgs[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
				if(i==0)
				{
				
					phno=msgs[i].getOriginatingAddress();
				}
				str4+=msgs[i].getMessageBody();
			}
			
				if(str4.equals("locate")||str4.equals("LOCATE")||str4.equals("Locate"))
				{
			//		Toast.makeText(ctx, "condi",Toast.LENGTH_SHORT).show();
					if(phno.equals(str)||phno.equals(str1)||phno.equals(str2))
					{
					//Toast.makeText(ctx,"Entered in condition",Toast.LENGTH_SHORT).show();
						sped.putString("C4",phno);
						sped.commit();
                       Intent location=new Intent(ctx,Locating.class);
                       location.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       ctx.startActivity(location);
				}
				}
			}
		
	
		
	}

}
