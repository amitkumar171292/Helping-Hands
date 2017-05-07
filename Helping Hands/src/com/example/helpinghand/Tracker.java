package com.example.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Tracker extends Activity {
	EditText e1;
	ImageButton ibocon;
	Button locate;
	SmsManager smsmgr;
	private static final String TAG = SetContacts.class.getSimpleName();
	private static final int REQUEST_CODE_PICK_CONTACTS = 1;
	private Uri uriContact;
	private String contactID;
	String contactNumber = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.track);
		e1 = (EditText) findViewById(R.id.etTrack);
		ibocon=(ImageButton)findViewById(R.id.ibTrack);
		locate=(Button)findViewById(R.id.bTrack);
		smsmgr = SmsManager.getDefault();
	}

	public void sendSms(View v) {
		String str = "";
		str = e1.getText().toString();
		if (str.equals("")) {
			Toast.makeText(getBaseContext(),
					"Nigga You have To Enter A Number to Track",
					Toast.LENGTH_SHORT).show();
		} else {
			smsmgr.sendTextMessage(str, null, "Locate", null, null);
			Toast.makeText(
					getBaseContext(),
					"Request to locate is sent. If you are trusted then the Location will follow",
					Toast.LENGTH_LONG).show();
		}
	}

	
	public void ocon(View v) {
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
			e1.setText(contactNumber);
			Log.d(TAG, "Contact Phone Number: " + contactNumber);

		}

	}
	
	
	
	
	
}
