package com.hutter.dialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.AdapterView;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PERMISSIONS_REQUEST = 1;
    private static final int CONTACTS_PERMISSIONS_REQUEST = 2;

    TextView tv_number;
    ListView listView ;

    Button b_1, b_2, b_3, b_4, b_5, b_6, b_7, b_8, b_9, b_0, b_clear, b_call;

    String number = "";

    ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;

    MediaPlayer mp;

    Cursor cursor ;
    String name, phonenumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSIONS_REQUEST);
            }
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS))
        {
            Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSIONS_REQUEST);
        }



        StoreContacts = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listview1);

        GetContactsIntoArrayList();
        Collections.sort(StoreContacts, String.CASE_INSENSITIVE_ORDER);

        //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.contacts, R.id.textview, StoreContacts);

        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.contacts, R.id.textview, StoreContacts){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(position %2 == 1)
                {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.parseColor("#555555"));
                }
                else
                {
                    // Set the background color for alternate row/item
                    view.setBackgroundColor(Color.parseColor("#222222"));
                }
                return view;
            }
        };

        listView.setAdapter(arrayAdapter);

        tv_number = (TextView) findViewById(R.id.tv_number);
        number = "\n";
        tv_number.setText(number);

        b_1 = (Button) findViewById(R.id.b_1);
        b_2 = (Button) findViewById(R.id.b_2);
        b_3 = (Button) findViewById(R.id.b_3);
        b_4 = (Button) findViewById(R.id.b_4);
        b_5 = (Button) findViewById(R.id.b_5);
        b_6 = (Button) findViewById(R.id.b_6);
        b_7 = (Button) findViewById(R.id.b_7);
        b_8 = (Button) findViewById(R.id.b_8);
        b_9 = (Button) findViewById(R.id.b_9);
        b_0 = (Button) findViewById(R.id.b_0);
        b_call = (Button) findViewById(R.id.b_call);
        b_clear = (Button) findViewById(R.id.b_clear);

        b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf1);
                mp.start();
                number = number + "1";
                tv_number.setText(number);
            }
        });

        b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf2);
                mp.start();
                number = number + "2";
                tv_number.setText(number);
            }
        });

        b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf3);
                mp.start();
                number = number + "3";
                tv_number.setText(number);
            }
        });

        b_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf4);
                mp.start();
                number = number + "4";
                tv_number.setText(number);
            }
        });

        b_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf5);
                mp.start();
                number = number + "5";
                tv_number.setText(number);
            }
        });

        b_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf6);
                mp.start();
                number = number + "6";
                tv_number.setText(number);
            }
        });

        b_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf7);
                mp.start();
                number = number + "7";
                tv_number.setText(number);
            }
        });

        b_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf8);
                mp.start();
                number = number + "8";
                tv_number.setText(number);
            }
        });

        b_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf9);
                mp.start();
                number = number + "9";
                tv_number.setText(number);
            }
        });

        b_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmf0);
                mp.start();
                number = number + "0";
                tv_number.setText(number);
            }
        });

        b_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmfpound);
                mp.start();
                Intent intent = new Intent(Intent.ACTION_CALL);
                //number = number.replaceAll("[^\\d]", "" );
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        b_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {  if (mp.isPlaying()) {  mp.stop(); mp.release(); } mp = null; }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.dtmfstar);
                mp.start();
                number = "\n";
                tv_number.setText(number);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(MainActivity.this, "You Clicked at " +StoreContacts.get(position), Toast.LENGTH_SHORT).show();
                String num = StoreContacts.get(position); //StoreContacts.get(position).substring(StoreContacts.get(position).indexOf("\n")+1);
                tv_number.setText(num);

                number = StoreContacts.get(position).substring(StoreContacts.get(position).indexOf("\n")+1).replaceAll("[^\\d]", "" );
            }

        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case CALL_PERMISSIONS_REQUEST: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "Call permission granted!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Call permission declined!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            case CONTACTS_PERMISSIONS_REQUEST: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "Read contacts permission granted!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Read contacts permission declined!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + "\n" + phonenumber);
        }

        cursor.close();

    }
}
