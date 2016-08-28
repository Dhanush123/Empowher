package com.x10host.dhanushpatel.empowher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

public class ChooseContacts extends Activity {
    public static final int REQUEST_CODE_PICK_CONTACT = 1;
    private int timeSelected;
    Spinner spinner;
    static SharedPreferences contactsPref;
//    Toolbar toolbar;

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);

//        toolbar=(Toolbar)findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);

        contactsPref = getSharedPreferences("MyContacts", Context.MODE_PRIVATE);
    }

    public void firstContactSelected(View view) {

        int   MAX_PICK_CONTACT = 10;
        //GlobalVariables mApp = ((GlobalVariables)getApplicationContext());

        Intent pickContactIntent1 = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent1.putExtra("additional", "phone-multi");
        pickContactIntent1.putExtra("maxRecipientCount", MAX_PICK_CONTACT);
        pickContactIntent1.putExtra("FromMMS", true);
        pickContactIntent1.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent1, PICK_CONTACT_REQUEST);
    }
    public void secondContactSelected(View view) {

        int   MAX_PICK_CONTACT = 10;

        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.putExtra("additional", "phone-multi");
        pickContactIntent.putExtra("maxRecipientCount", MAX_PICK_CONTACT);
        pickContactIntent.putExtra("FromMMS", true);
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
    public void thirdContactSelected(View view) {
        int   MAX_PICK_CONTACT = 10;

        Intent pickContactIntent3 = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent3.putExtra("additional", "phone-multi");
        pickContactIntent3.putExtra("maxRecipientCount", MAX_PICK_CONTACT);
        pickContactIntent3.putExtra("FromMMS", true);
        pickContactIntent3.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent3, PICK_CONTACT_REQUEST);
    }

    /**
     * A helper method for picking contacts
     */
    public void startPickContactsIntent() {
        int   MAX_PICK_CONTACT = 10;
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.putExtra("additional", "phone-multi");
        pickContactIntent.putExtra("maxRecipientCount", MAX_PICK_CONTACT);
        pickContactIntent.putExtra("FromMMS", true);
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                // Do something with the contact here
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                if((com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne==0))
                {
                    com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne = Long.valueOf(number.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
                }
                else if(com.x10host.dhanushpatel.empowher.GlobalVariables.contactTwo==0&& com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne!=0)
                {
                    com.x10host.dhanushpatel.empowher.GlobalVariables.contactTwo = Long.valueOf(number.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
                }
                else if(com.x10host.dhanushpatel.empowher.GlobalVariables.contactThree==0&& com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne!=0&& com.x10host.dhanushpatel.empowher.GlobalVariables.contactTwo!=0)
                {
                    com.x10host.dhanushpatel.empowher.GlobalVariables.contactThree = Long.valueOf(number.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
                }

                Log.i("Contact:", number);
                // Do something with the phone number...
//                Log.i("time", GlobalVariables.timeLimit+"");

                //check if all contacts have been set
                if(com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne!=0&& com.x10host.dhanushpatel.empowher.GlobalVariables.contactTwo!=0&& com.x10host.dhanushpatel.empowher.GlobalVariables.contactThree!=0)
                {
                    //SharedPrefs set here so this activity doesn't show up if contacts already choosen
                    SharedPreferences.Editor edit = contactsPref.edit();
                    edit.putString("contactsSetStatus","contactsSet");
                    edit.commit();
                    text();
                    Intent intent = new Intent(ChooseContacts.this, com.x10host.dhanushpatel.empowher.CameraActivity.class);
                }
            }
        }
    }



    public void text() {
        SmsManager sm = SmsManager.getDefault();//GlobalVariables.googleMapsAddress

       // String msg = "I am drunk and in need of assistance. I am at: "+GlobalVariables.location;
        if(com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne+""!=null) {
            sm.sendTextMessage((String.valueOf(com.x10host.dhanushpatel.empowher.GlobalVariables.contactOne)), null, "I am feeling threatened and/or someone is behaving violently towards me. Please come and help as soon as possible! I am at: "+ com.x10host.dhanushpatel.empowher.GlobalVariables.location, null, null);
        }
        if((com.x10host.dhanushpatel.empowher.GlobalVariables.contactTwo+"")!=null) {
            sm.sendTextMessage((String.valueOf(com.x10host.dhanushpatel.empowher.GlobalVariables.contactTwo)), null, "I am feeling threatened and/or someone is behaving violently towards me. Please come and help as soon as possible! I am at: "+ com.x10host.dhanushpatel.empowher.GlobalVariables.location, null, null);
        }
        if((com.x10host.dhanushpatel.empowher.GlobalVariables.contactThree+"")!=null) {
            sm.sendTextMessage((String.valueOf(com.x10host.dhanushpatel.empowher.GlobalVariables.contactThree)), null, "I am feeling threatened and/or someone is behaving violently towards me. Please come and help as soon as possible! I am at: "+ com.x10host.dhanushpatel.empowher.GlobalVariables.location, null, null);
        }
    }
}
