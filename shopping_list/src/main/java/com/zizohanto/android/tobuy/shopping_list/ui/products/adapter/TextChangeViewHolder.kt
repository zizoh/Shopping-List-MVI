package com.zizohanto.android.tobuy.shopping_list.ui.products.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class TextChangeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun isValidTextChange(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ): Boolean {
        return !(s.isNullOrEmpty() && start == 0 && before == 0 && count == 0)
    }
}