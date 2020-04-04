package com.anamaria.onlineshop

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    private var bookId: String? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_book)

        bookId = intent.getStringExtra("bookId")

        val item: Book = items.find { it.id == bookId }!!

        author.text = item.author
        book_title.text = item.title
        price.text = "${item.price} $"
        rating.text = "Rating: ${item.rating}"
        description.text = item.description
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