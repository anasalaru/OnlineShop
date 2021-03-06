package com.anamaria.onlineshop

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.anamaria.onlineshop.SettingsActivity.Companion.REGION_USA
import com.anamaria.onlineshop.data.Book
import com.anamaria.onlineshop.data.BooksApiService
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    private var bookId: String? = null
    private lateinit var item: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_book)

        bookId = intent.getStringExtra("bookId")

        val items = BooksApiService.getBooks()
        item = items.find { it.id == bookId }!!

        author.text = item.author
        book_title.text = item.title

        val currency = if (getRegion() == REGION_USA) "$" else "€"
        price.text = "${item.price} $currency"
        rating.text = "Rating: ${item.rating}"
        description.text = item.description

        title = item.title

        add_review.setOnClickListener {
            openReviewDialog()
        }
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

        val currency = if (getRegion() == REGION_USA) "$" else "€"

        price.text = "${item.price} $currency"
    }

    private fun getRegion(): String =
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(SettingsActivity.REGION, SettingsActivity.REGION_USA)!!

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_book_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun openReviewDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_review, null)
        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Submit") { d, _ -> submitReview(d) }
            .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .create()
            .show()

    }

    private fun submitReview(dialog: DialogInterface) {
        dialog.dismiss()
        Toast.makeText(this, "Review submitted successfully.", Toast.LENGTH_SHORT).show()
    }
}