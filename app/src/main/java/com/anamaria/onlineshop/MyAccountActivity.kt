package com.anamaria.onlineshop

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.anamaria.onlineshop.data.User
import com.anamaria.onlineshop.data.UserUIModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MyAccountActivity : AppCompatActivity() {
    companion object {
        const val CAMERA_ACCESS_REQUEST_CODE = 1
        const val REQUEST_IMAGE_CAPTURE = 2
    }

    private var currentPhotoPath: String? = null
    private var user: UserUIModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_account)
        title = "My Account"
    }

    override fun onStart() {
        super.onStart()

        loadAvatar()

        name.text = "Username"

        edit_bg.setOnClickListener {
            onEditAvatarClick()
        }

        loadUser()
    }

    private fun loadUser() {
        lifecycleScope.launch {
            user = userDao.getMe()
            loadAvatar()
        }
    }

    private fun loadAvatar() {
        Glide.with(this)
            .load(user?.avatar)
            .circleCrop()
            .placeholder(R.drawable.ic_account_circle)
            .into(avatar)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_ACCESS_REQUEST_CODE && grantResults.any { it == PackageManager.PERMISSION_GRANTED }) openCamera()
        else super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun onEditAvatarClick() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA), CAMERA_ACCESS_REQUEST_CODE
            )
        } else openCamera()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile()
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.anamaria.onlineshop.fileprovider",
            photoFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }


    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File = File(applicationContext.filesDir, "images")
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            applicationContext.filesDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val photo = File(currentPhotoPath).readBytes()
            lifecycleScope.launch {
                userDao.persistMe(UserUIModel(null, photo))
                loadUser()
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }
}
