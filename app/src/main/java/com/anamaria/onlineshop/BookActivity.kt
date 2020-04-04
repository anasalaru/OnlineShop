package com.anamaria.onlineshop

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    private var bookId: String? = null
    private lateinit var item: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_book)

        bookId = intent.getStringExtra("bookId")

        item = items.find { it.id == bookId }!!

        author.text = item.author
        book_title.text = item.title
        price.text = "${item.price} $"
        rating.text = "Rating: ${item.rating}"
        description.text = item.description

        title = item.title
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString("bookId", bookId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredBookId = savedInstanceState?.getString("bookId")
        Log.d(TAG, "restored book id is $restoredBookId")
        bookId = restoredBookId
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.share) {
            shareBook()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun shareBook() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this cool book I found: ${item.title} by ${item.author}. And it's only ${item.price}$! Go buy it Cheap Book Shop!"
        )
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, null))
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_book_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}