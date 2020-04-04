package com.anamaria.onlineshop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter(private val context: Context, private var items: List<Book>, private val onClick: (String) -> Unit) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)
        val item: Book = items[position]
        with(view) {
            title.text = item.title
            author.text = item.author
            price.text = "${item.price} $"
        }

        view.setOnClickListener {
            onClick(items[position].id)
        }
            return view
    }

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

}