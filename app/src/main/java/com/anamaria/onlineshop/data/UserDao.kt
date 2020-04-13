package com.anamaria.onlineshop.data

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class UserDao(context: Context) {
    private lateinit var realm: Realm
    private lateinit var realmConfig: RealmConfiguration

    val bgThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    init {
        CoroutineScope(bgThread).launch {
            Realm.init(context)
            realmConfig = RealmConfiguration.Builder().build()
            realm = Realm.getInstance(realmConfig)
        }
    }

    suspend fun getMe(): UserUIModel =
        withContext(bgThread) {
            val dbUser = realm.where<User>(User::class.java).findFirst()
            UserMapper.dbToUI(dbUser)
        }

    suspend fun persistMe(user: UserUIModel) {
        withContext(bgThread) {
            val dbUser = UserMapper.uiToDb(user)
            with(realm) {
                beginTransaction()
                copyToRealm(dbUser)
                commitTransaction()
            }
        }
    }
}

class UserUIModel(val name: String?, val avatar: ByteArray?)
object UserMapper {
    fun uiToDb(u: UserUIModel): User = User(u.name, u.avatar)
    fun dbToUI(u: User?): UserUIModel = UserUIModel(u?.name, u?.avatar)
}