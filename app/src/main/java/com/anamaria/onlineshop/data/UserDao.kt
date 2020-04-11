package com.anamaria.onlineshop.data

import android.content.Context
import io.realm.Realm
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserDao(context: Context) {
    private val realm: Realm

    init {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    suspend fun getMe(): User? =
        withContext(IO) {
            realm.where<User>(User::class.java).findFirst()
        }

    suspend fun persistMe(user: User) {
        withContext(IO) {
            with(realm) {
                beginTransaction()
                copyToRealm(user)
                commitTransaction()
            }
        }
    }
}