package com.anamaria.onlineshop

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.anamaria.onlineshop.SettingsActivity.Companion.REGION_USA
import com.anamaria.onlineshop.data.BooksApiService
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "ONLINESHOPLOG"

class MainActivity : AppCompatActivity() {

    private val items = BooksApiService.getBooks()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        products_list.adapter = BooksAdapter(this, items, getRegion()) {
            val intent = Intent(this@MainActivity, BookActivity::class.java)
            intent.putExtra("bookId", it)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.account -> openMyAccount()
            R.id.settings -> openSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun getRegion(): String =
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(SettingsActivity.REGION, REGION_USA)!!

    private fun openMyAccount() {
        startActivity(Intent(this, MyAccountActivity::class.java))
    }
}