package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet
) : AppCompatEditText(context, attributeSet) {

    private var changed = false

    fun textHasChanged() = changed

    fun setChanged(isChanged: Boolean) {
        changed = isChanged
    }
}