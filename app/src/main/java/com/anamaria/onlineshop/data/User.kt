package com.anamaria.onlineshop.data

import io.realm.RealmObject

open class User(
    var name: String,
    var avatar: ByteArray
): RealmObject()