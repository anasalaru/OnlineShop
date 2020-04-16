package com.anamaria.onlineshop.data

import io.realm.RealmObject

open class User(
    var name: String? = null,
    var avatar: ByteArray? = null
): RealmObject()