package com.kotlindemo.main

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.kotlindemo.common.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class ImageSelectionActivity : AppCompatActivity() {

    val CODE_PERMISSION = 1
    val CODE_PICK_IMAGE = 2
    val CODE_CAPTURE_IMAGE = 3
    var mUri: Uri? = null
    var isPickImg: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initObj();
    }

    fun initObj() {
        btnSelect.setOnClickListener {
            isPickImg = true
            if (initPermission()) {
                selectImage()
            }
        }

        btnCapture.setOnClickListener {
            isPickImg = false
            if (initPermission()) {
                capturePhoto()
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CODE_PICK_IMAGE)
    }

    private fun initPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ) {
                return true
            } else {
                val permission = arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                )
                requestPermissions(permission, CODE_PERMISSION)
                return false
            }
        } else {
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODE_PICK_IMAGE -> {
                imageView.setImageURI(data?.data)
            }
            CODE_CAPTURE_IMAGE -> {
                imageView.setImageURI(mUri)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CODE_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isPickImg) selectImage() else capturePhoto()
                } else {
                    showToast(this,"Permission denied")
                }
            }
        }
    }

    private fun capturePhoto() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        mUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(cameraIntent, CODE_CAPTURE_IMAGE)
    }
}