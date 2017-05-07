package com.example.helpinghand;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application
{
	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this,"OVeYJQIBns2H2GKVSx8nfj4Gn9n99xSg3ygjum3L", "yIrkNHMmRNfnKwM7AmAeXGbdAUlDa10859delx6o");


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		
	}


}
