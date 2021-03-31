package com.mergenc.nationbook

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add_place.*
import kotlinx.android.synthetic.main.activity_nation_info.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.jar.Manifest

class AddPlaceActivity : AppCompatActivity() {
    var selectedPicture: Uri? = null
    var selectedBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        // Pass drawable between NationInfo and AddPlaceActivity;
        val mapImage = resources.getDrawable(intent.getIntExtra("map button", -1))
        mapImageView.setImageDrawable(mapImage)
    }

    fun save(view: View) {
        val intentForInfo = intent
        val countryName = intentForInfo.getStringExtra("country name info")
        val placeName = editPlaceName.text.toString()
        val location = editLocation.text.toString()
        val description = editDescription.text.toString()

        if (selectedBitmap != null) {
            val resizedBitmap = resizeImage(selectedBitmap!!, 300)
            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byteArray = outputStream.toByteArray() // image for SQLite (byteArray)

            val database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null)

            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS placesTR (idTR INTEGER PRIMARY KEY, placenameTR VARCHAR, locationTR VARCHAR, descriptionTR VARCHAR, imageTR BLOB)")
                val SQLStringTR =
                    "INSERT INTO placesTR (placenameTR, locationTR, descriptionTR, imageTR) VALUES (?, ?, ?, ?)"
                val statementTR = database.compileStatement(SQLStringTR)
                statementTR.bindString(1, placeName)
                statementTR.bindString(2, location)
                statementTR.bindString(3, description)
                statementTR.bindBlob(4, byteArray)

                statementTR.execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(applicationContext, "Please select an image.", Toast.LENGTH_SHORT)
                .show()
        }

        finish()
    }

    fun resizeImage(image: Bitmap, maxSize: Int): Bitmap {
        var imageWidth = image.width
        var imageHeight = image.height

        val bitmapRatio: Double = imageWidth.toDouble() / imageHeight.toDouble()
        if (bitmapRatio > 1) {
            imageWidth = maxSize
            val resizedHeight = imageWidth / bitmapRatio
            imageHeight = resizedHeight.toInt()
        } else {
            imageHeight = maxSize
            val resizedWidth = imageHeight * bitmapRatio
            imageWidth = resizedWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image, imageWidth, imageHeight, true)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPicture = data.data

            try {
                if (selectedPicture != null) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        val source =
                            ImageDecoder.createSource(this.contentResolver, selectedPicture!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        mapImageView.setImageBitmap(selectedBitmap)
                    } else {
                        selectedBitmap =
                            MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                selectedPicture
                            )
                        mapImageView.setImageBitmap(selectedBitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}