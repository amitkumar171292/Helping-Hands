package com.example.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity 
{
	EditText e1,e2,e3,e4;
	Button b1;
	SharedPreferences sp;
	SharedPreferences.Editor sped;
@Override
protected void onCreate(Bundle savedInstanceState) 
{
    // TODO Auto-generated method stub
	setContentView(R.layout.sign_up);
	super.onCreate(savedInstanceState);
	e1=(EditText)findViewById(R.id.user);
	e2=(EditText)findViewById(R.id.password);
	e3=(EditText)findViewById(R.id.cpassword);
	e4=(EditText)findViewById(R.id.pincode);
	b1=(Button)findViewById(R.id.bs);
	sp=getSharedPreferences("user",MODE_PRIVATE);
	sped=sp.edit();
	}

public void saveKaro(View v)
{  String s1="",s2="",s3="",s4="";
   s1=e1.getText().toString();
   s2=e2.getText().toString();
   s3=e3.getText().toString();
   s4=e4.getText().toString();
   if(s1.equals("")||s2.equals("")||s4.equals(""))
   {
	   Toast.makeText(getBaseContext(), "Fields Cannot Be Left Blank", Toast.LENGTH_SHORT).show();
   }
   else
   {
	   if(s2.equals(s3))
	   {
	   sped.putString("user",s1);
	   sped.putString("password",s2);
	   sped.putString("pincode",s4);
	   sped.putString("key5", "value5");
	   sped.commit();
	   Toast.makeText(getBaseContext(), "Signed In Successfully", Toast.LENGTH_SHORT).show();
	   Intent open=new Intent(getBaseContext(),SetContacts.class);
	   startActivity(open);
	   }
	   else
	   {
		   Toast.makeText(getBaseContext(), "Password and Confirm Password Does Not Match", Toast.LENGTH_SHORT).show();
	   }
      
   }
	
}

protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	finish();
}

}

