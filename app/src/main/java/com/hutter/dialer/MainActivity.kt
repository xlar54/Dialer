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
    private lateinit var tvNumber: TextView
    private lateinit var listView: ListView
    private lateinit var b1: Button
    private lateinit var b2: Button
    private lateinit var b3: Button
    private lateinit var b4: Button
    private lateinit var b5: Button
    private lateinit var b6: Button
    private lateinit var b7: Button
    private lateinit var b8: Button
    private lateinit var b9: Button
    private lateinit var b0: Button
    private lateinit var bClear: Button
    private lateinit var bCall: Button
    private var number = ""
    private var storeContacts: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null

    private var cursor: Cursor? = null
    private var name: String? = null
    private var phoneNumber: String? = null
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
        storeContacts = ArrayList()
        listView = findViewById(R.id.listview1)
        getContactsIntoArrayList()
        storeContacts?.let { Collections.sort(it, java.lang.String.CASE_INSENSITIVE_ORDER) }

        //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.contacts, R.id.textview, StoreContacts);
        arrayAdapter = object : ArrayAdapter<String>(this@MainActivity, R.layout.contacts, R.id.textview, storeContacts!!) {
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

        listView.adapter = arrayAdapter
        tvNumber = findViewById(R.id.tv_number)
        number = "\n"
        tvNumber.text = number
        b1 = findViewById(R.id.b_1)
        b2 = findViewById(R.id.b_2)
        b3 = findViewById(R.id.b_3)
        b4 = findViewById(R.id.b_4)
        b5 = findViewById(R.id.b_5)
        b6 = findViewById(R.id.b_6)
        b7 = findViewById(R.id.b_7)
        b8 = findViewById(R.id.b_8)
        b9 = findViewById(R.id.b_9)
        b0 = findViewById(R.id.b_0)
        bCall = findViewById(R.id.b_call)
        bClear = findViewById(R.id.b_clear)

        b1.setOnClickListener {
            playMedia(mp, R.raw.dtmf1)
            number += "1"
            tvNumber.text = number
        }
        b2.setOnClickListener {
            playMedia(mp, R.raw.dtmf2)
            number += "2"
            tvNumber.text = number
        }
        b3.setOnClickListener {
            playMedia(mp, R.raw.dtmf3)
            number += "3"
            tvNumber.text = number
        }
        b4.setOnClickListener {
            playMedia(mp, R.raw.dtmf4)
            number += "4"
            tvNumber.text = number
        }
        b5.setOnClickListener {
            playMedia(mp, R.raw.dtmf5)
            number += "5"
            tvNumber.text = number
        }
        b6.setOnClickListener {
            playMedia(mp, R.raw.dtmf6)
            number += "6"
            tvNumber.text = number
        }
        b7.setOnClickListener {
            playMedia(mp, R.raw.dtmf7)
            number += "7"
            tvNumber.text = number
        }
        b8.setOnClickListener {
            playMedia(mp, R.raw.dtmf8)
            number += "8"
            tvNumber.text = number
        }
        b9.setOnClickListener {
            playMedia(mp, R.raw.dtmf9)
            number += "9"
            tvNumber.text = number
        }
        b0.setOnClickListener {
            playMedia(mp, R.raw.dtmf0)
            number += "0"
            tvNumber.text = number
        }
        bCall.setOnClickListener {
            playMedia(mp, R.raw.dtmfpound)
            val intent = Intent(Intent.ACTION_CALL)
            //number = number.replaceAll("[^\\d]", "" );
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
        bClear.setOnClickListener {
            playMedia(mp, R.raw.dtmfstar)
            number = "\n"
            tvNumber.text = number
        }
        listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                //Toast.makeText(MainActivity.this, "You Clicked at " +StoreContacts.get(position), Toast.LENGTH_SHORT).show();
                val num = storeContacts!![position] //StoreContacts.get(position).substring(StoreContacts.get(position).indexOf("\n")+1);
                tvNumber.text = num
                number = storeContacts!![position].substring(storeContacts!![position].indexOf("\n") + 1).replace("[^\\d]".toRegex(), "")
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

    private fun getContactsIntoArrayList() {
        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val nameIndex = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            if (nameIndex > 0) name = cursor!!.getString(nameIndex)
            if (phoneNumberIndex > 0) phoneNumber = cursor!!.getString(phoneNumberIndex)
            storeContacts!!.add("""
    $name
    $phoneNumber
    """.trimIndent())
        }
        cursor!!.close()
    }

    companion object {
        private const val CALL_PERMISSIONS_REQUEST = 1
        private const val CONTACTS_PERMISSIONS_REQUEST = 2
    }
}