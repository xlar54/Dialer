package com.hutter.dialer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var tv_number: TextView
    private lateinit var listView: ListView
    private lateinit var b_1: Button
    private lateinit var b_2: Button
    private lateinit var b_3: Button
    private lateinit var b_4: Button
    private lateinit var b_5: Button
    private lateinit var b_6: Button
    private lateinit var b_7: Button
    private lateinit var b_8: Button
    private lateinit var b_9: Button
    private lateinit var b_0: Button
    private lateinit var b_clear: Button
    private lateinit var b_call: Button
    private var number = ""
    private var StoreContacts: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null

    private var cursor: Cursor? = null
    private var name: String? = null
    private var phonenumber: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSIONS_REQUEST)
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, Manifest.permission.READ_CONTACTS)) {
            Toast.makeText(this@MainActivity, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_CONTACTS), CONTACTS_PERMISSIONS_REQUEST)
        }
        StoreContacts = ArrayList()
        listView = findViewById(R.id.listview1)
        GetContactsIntoArrayList()
        StoreContacts?.let { Collections.sort(it, java.lang.String.CASE_INSENSITIVE_ORDER) }

        //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.contacts, R.id.textview, StoreContacts);
        arrayAdapter = object : ArrayAdapter<String>(this@MainActivity, R.layout.contacts, R.id.textview, StoreContacts!!) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                // Get the current item from ListView
                val view = super.getView(position, convertView, parent)
                if (position % 2 == 1) {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.parseColor("#555555"))
                } else {
                    // Set the background color for alternate row/item
                    view.setBackgroundColor(Color.parseColor("#222222"))
                }
                return view
            }
        }

        val mp = MediaPlayer.create(applicationContext, R.raw.dtmf1)

        listView.setAdapter(arrayAdapter)
        tv_number = findViewById(R.id.tv_number)
        number = "\n"
        tv_number.setText(number)
        b_1 = findViewById(R.id.b_1)
        b_2 = findViewById(R.id.b_2)
        b_3 = findViewById(R.id.b_3)
        b_4 = findViewById(R.id.b_4)
        b_5 = findViewById(R.id.b_5)
        b_6 = findViewById(R.id.b_6)
        b_7 = findViewById(R.id.b_7)
        b_8 = findViewById(R.id.b_8)
        b_9 = findViewById(R.id.b_9)
        b_0 = findViewById(R.id.b_0)
        b_call = findViewById(R.id.b_call)
        b_clear = findViewById(R.id.b_clear)

        b_1.setOnClickListener {
            playMedia(mp, R.raw.dtmf1)
            number += "1"
            tv_number.text = number
        }
        b_2.setOnClickListener {
            playMedia(mp, R.raw.dtmf2)
            number += "2"
            tv_number.text = number
        }
        b_3.setOnClickListener {
            playMedia(mp, R.raw.dtmf3)
            number += "3"
            tv_number.text = number
        }
        b_4.setOnClickListener {
            playMedia(mp, R.raw.dtmf4)
            number += "4"
            tv_number.text = number
        }
        b_5.setOnClickListener {
            playMedia(mp, R.raw.dtmf5)
            number += "5"
            tv_number.setText(number)
        }
        b_6.setOnClickListener {
            playMedia(mp, R.raw.dtmf6)
            number += "6"
            tv_number.text = number
        }
        b_7.setOnClickListener {
            playMedia(mp, R.raw.dtmf7)
            number += "7"
            tv_number.text = number
        }
        b_8.setOnClickListener {
            playMedia(mp, R.raw.dtmf8)
            number += "8"
            tv_number.text = number
        }
        b_9.setOnClickListener {
            playMedia(mp, R.raw.dtmf9)
            number += "9"
            tv_number.text = number
        }
        b_0.setOnClickListener {
            playMedia(mp, R.raw.dtmf0)
            number += "0"
            tv_number.text = number
        }
        b_call.setOnClickListener {
            playMedia(mp, R.raw.dtmfpound)
            val intent = Intent(Intent.ACTION_CALL)
            //number = number.replaceAll("[^\\d]", "" );
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
        b_clear.setOnClickListener {
            playMedia(mp, R.raw.dtmfstar)
            number = "\n"
            tv_number.text = number
        }
        listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                //Toast.makeText(MainActivity.this, "You Clicked at " +StoreContacts.get(position), Toast.LENGTH_SHORT).show();
                val num = StoreContacts!![position] //StoreContacts.get(position).substring(StoreContacts.get(position).indexOf("\n")+1);
                tv_number.setText(num)
                number = StoreContacts!![position].substring(StoreContacts!![position].indexOf("\n") + 1).replace("[^\\d]".toRegex(), "")
            }
    }

    private fun playMedia(mp: MediaPlayer, dtmfVal: Int) {

        if (mp.isPlaying) {
            mp.stop()
        }
        mp.reset()
        val afd: AssetFileDescriptor = applicationContext.resources.openRawResourceFd(dtmfVal)
        mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mp.prepare()
        afd.close()

        mp.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CALL_PERMISSIONS_REQUEST -> {
                run {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this@MainActivity, "Call permission granted!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Call permission declined!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                run {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this@MainActivity, "Read contacts permission granted!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Read contacts permission declined!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            CONTACTS_PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this@MainActivity, "Read contacts permission granted!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Read contacts permission declined!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    fun GetContactsIntoArrayList() {
        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val nameIndex = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            if (nameIndex > 0) name = cursor!!.getString(nameIndex)
            if (phoneNumberIndex > 0) phonenumber = cursor!!.getString(phoneNumberIndex)
            StoreContacts!!.add("""
    $name
    $phonenumber
    """.trimIndent())
        }
        cursor!!.close()
    }

    companion object {
        private const val CALL_PERMISSIONS_REQUEST = 1
        private const val CONTACTS_PERMISSIONS_REQUEST = 2
    }
}