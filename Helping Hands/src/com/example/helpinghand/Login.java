package com.example.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{
    Button subm;
    EditText e1,e2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initialize();
	}

	private void initialize(){
		subm=(Button)findViewById(R.id.bSubm);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		subm.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.bSubm:
			SharedPreferences shared = getSharedPreferences("user",MODE_PRIVATE);
			String string_temp = shared.getString("user","pepsi");
			String string_temp1 = shared.getString("password","pepsi");
			SharedPreferences shared1 = getSharedPreferences("CONTACTS",MODE_PRIVATE);
			String con = shared1.getString("CON","pepsi");
			if(string_temp.equals(e1.getText().toString())&&string_temp1.equals(e2.getText().toString()))
			{
			if(con.equals("value3")){
				Intent open2=new Intent(getBaseContext(),MainPage.class);
				   startActivity(open2);
			}
			else{
				 Intent open=new Intent(getBaseContext(),SetContacts.class);
				   startActivity(open);
				   }
			}
			else{
				Toast.makeText(getBaseContext(), "Wrong Credentials!!!!!!", Toast.LENGTH_SHORT).show();
			}
			break;
	}

}
	
	protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	finish();
}
}