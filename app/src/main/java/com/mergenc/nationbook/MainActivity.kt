package com.mergenc.nationbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

/*
* Nationbook — Best places to visit in the world;
* Mehmet Emin Ergenc — mergencdev;
* https://github.com/mergencdev;
* https://twitter.com/mergencdev;
*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun turkey(view: View) { // Turkey's onClick;
        val intent = Intent(this, NationInfo::class.java)
        intent.putExtra("country name", "Turkey")
        intent.putExtra("country flag", R.drawable.flag_turkey)
        startActivity(intent)
        // putting "country name" and "country flag" to NationInfo.kt;
    }

    fun australia(view: View) { // Australia's onClick;
        val intent = Intent(this, NationInfo::class.java)
        intent.putExtra("country name", "Australia")
        intent.putExtra("country flag", R.drawable.flag_australia)
        startActivity(intent)
    }
}

// 29 march 2021, 9.00 a.m. to 3.50 p.m.: 361. Menu eklemek
// 30 march 2021, 4.00 p.m. to 9.00 p.m.:
// 31 march 2021, 2.00 p.m. to