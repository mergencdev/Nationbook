package com.mergenc.nationbook

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nation_info.*
import java.lang.Exception

class NationInfo : AppCompatActivity() {
    var selectedCountry: Uri? = null
    var selectedCountryBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nation_info)

        val intent = intent
        val placeNameListTR = ArrayList<String>()
        val placeIDListTR = ArrayList<Int>()

        // simple_list_item_1 listView;
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, placeNameListTR)
        listView.adapter = arrayAdapter

        val placeNameListAU = ArrayList<String>()
        val placeIDListAU = ArrayList<Int>()

        // getting "country name" from MainActivity.;
        val countryInfo = intent.getStringExtra("country name")
        textView.text = countryInfo
        val intentForInfo = Intent(this, AddPlaceActivity::class.java)
        intentForInfo.putExtra("country name info", countryInfo)


        // Getting "country flag" (drawable) from MainActivity.;
        // this method's src: https://stackoverflow.com/questions/8407336/how-to-pass-drawable-between-activities
        val flag = resources.getDrawable(intent.getIntExtra("country flag", -1))
        imageView7.setImageDrawable(flag)

        try {
            val database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM placesTR", null)
            val placeNameIndexTR = cursor.getColumnIndex("placenameTR")
            val IDIndexTR = cursor.getColumnIndex("idTR")

            while (cursor.moveToNext()) {
                placeNameListTR.add(cursor.getString(placeNameIndexTR))
                placeIDListTR.add(cursor.getInt(IDIndexTR))
            }

            arrayAdapter.notifyDataSetChanged() // ??
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Menu: Inflater;
        val addPlaceInflater = menuInflater
        addPlaceInflater.inflate(R.menu.add_place, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Menu item: onClick;
        val addPlaceIntent = Intent(this, AddPlaceActivity::class.java)
        val countryName = intent.getStringExtra("country name")
        if (item.itemId == R.id.add_place_item) {
            if (countryName == "Turkey") {
                addPlaceIntent.putExtra("map button", R.drawable.map_turkey)
                startActivity(addPlaceIntent)
            } else if (countryName == "Australia") {
                addPlaceIntent.putExtra("map button", R.drawable.map_australia)
                startActivity(addPlaceIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}