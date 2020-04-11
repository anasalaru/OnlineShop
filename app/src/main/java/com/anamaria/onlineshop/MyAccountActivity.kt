package com.anamaria.onlineshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_account)
        title = "My Account"
    }
}