package com.example.helpinghand;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SetContacts extends Activity implements OnClickListener 
{
	private static final String TAG = SetContacts.class.getSimpleName();
	private static final int REQUEST_CODE_PICK_CONTACTS = 1;
	private Uri uriContact;
	private String contactID;
	ImageButton ib1, ib2, ib3;
	Button save,can;
	EditText e1, eu = null;
	EditText e2;
	EditText e3;
	String contactNumber = null;
	SharedPreferences sp;
	SharedPreferences.Editor sped;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_contacts);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		ib1 = (ImageButton) findViewById(R.id.contact1);
		ib2 = (ImageButton) findViewById(R.id.contact2);
		ib3 = (ImageButton) findViewById(R.id.contact3);
		e1 = (EditText) findViewById(R.id.editText1);
		e2 = (EditText) findViewById(R.id.editText2);
		e3 = (EditText) findViewById(R.id.editText3);
		save=(Button)findViewById(R.id.button4);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
        save.setOnClickListener(this); 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.contact1:
			eu = e1;
			retrieveContactNumber();
			break;

		case R.id.contact2:
			eu = e2;
			retrieveContactNumber();

			break;
		case R.id.contact3:
			eu = e3;
			retrieveContactNumber();

			break;
		case R.id.button4:
			sp = getSharedPreferences("CONTACTS", MODE_PRIVATE);
			sped = sp.edit();
            String str="",str1="",str2="";
            str=e1.getText().toString();
            str1=e2.getText().toString();
            str2=e3.getText().toString();
            if(str.equals("")||str1.equals("")||str2.equals(""))
            {
            	Toast.makeText(getBaseContext(),"Fields Cannot Be Left Blank",Toast.LENGTH_SHORT).show();	
            }
            else
            { 	
			sped.putString("C1", str);
			sped.putString("C2", str1);
			sped.putString("C3", str2);
            sped.putString("CON", "value3");//for not repeating set contacts for multiple login
			sped.commit();

			Toast.makeText(getBaseContext(), "contacts saved",
					Toast.LENGTH_SHORT).show();
			Intent open=new Intent(getBaseContext(),MainPage.class);
			   startActivity(open);			
            }
		}

	}

	private void retrieveContactNumber() {
		// TODO Auto-generated method stub

		startActivityForResult(new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI),
				REQUEST_CODE_PICK_CONTACTS);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_PICK_CONTACTS
				&& resultCode == RESULT_OK) {
			Log.d(TAG, "Response: " + data.toString());
			uriContact = data.getData();

			retrieveContactNumber1();

		}

	}

	private void retrieveContactNumber1() {
		// TODO Auto-generated method stub

		// getting contacts ID
		Cursor cursorID = getContentResolver().query(uriContact,
				new String[] { ContactsContract.Contacts._ID }, null, null,
				null);

		if (cursorID.moveToFirst()) {

			contactID = cursorID.getString(cursorID
					.getColumnIndex(ContactsContract.Contacts._ID));
		}

		cursorID.close();

		Log.d(TAG, "Contact ID: " + contactID);

		// Using the contact ID now we will get contact phone number
		Cursor cursorPhone = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },

				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
						+ ContactsContract.CommonDataKinds.Phone.TYPE + " = "
						+ ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

				new String[] { contactID }, null);

		if (cursorPhone.moveToFirst()) {
			contactNumber = cursorPhone
					.getString(cursorPhone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		}

		cursorPhone.close();
		eu.setText(contactNumber);
		Log.d(TAG, "Contact Phone Number: " + contactNumber);
	}
	public void clearKaro(View v)
	{
	e1.setText("");
	e2.setText("");
	e3.setText("");
	}
	
	
	

}



