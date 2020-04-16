package com.anamaria.onlineshop

import android.app.Application
import com.anamaria.onlineshop.data.UserDao

lateinit var userDao: UserDao

class OnlineShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        userDao = UserDao(this)
    }
}