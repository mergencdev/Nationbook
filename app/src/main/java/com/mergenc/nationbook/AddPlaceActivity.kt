package com.mergenc.nationbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_place.*
import kotlinx.android.synthetic.main.activity_nation_info.*

class AddPlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        // Pass drawable between NationInfo and AddPlaceActivity;
        val mapImage = resources.getDrawable(intent.getIntExtra("map button", -1))
        mapImageView.setImageDrawable(mapImage)
    }
}