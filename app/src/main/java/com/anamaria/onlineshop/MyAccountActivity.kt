package com.anamaria.onlineshop

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_my_account.*

class MyAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_account)
        title = "My Account"
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .load(Uri.EMPTY)
            .centerCrop()
            .placeholder(R.drawable.ic_account_circle)
            .into(avatar)

        name.text = "Username"
    }
}