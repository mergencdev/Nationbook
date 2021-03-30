package com.mergenc.nationbook

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add_place.*
import kotlinx.android.synthetic.main.activity_nation_info.*
import java.util.jar.Manifest

class AddPlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        // Pass drawable between NationInfo and AddPlaceActivity;
        val mapImage = resources.getDrawable(intent.getIntExtra("map button", -1))
        mapImageView.setImageDrawable(mapImage)
    }

    fun save(view: View) {

    }

    fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) { // If 'READ_EXTERNAL_STORAGE' permission NOT granted - izin verilmediyse;
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1
            ) // Request permissions - izin ister;
        } else { // Open gallery
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, 2)

        }
    }

    override fun onRequestPermissionsResult( // Result of the request permission - Izin isteginin sonucunda yapilacaklar;
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) { // Is the request code 1, request permissions - yani izin verilmediyse;
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentToGallery, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}