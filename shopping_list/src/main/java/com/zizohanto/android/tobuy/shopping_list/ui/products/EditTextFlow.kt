package com.zizohanto.android.tobuy.shopping_list.ui.products

import android.widget.EditText
import kotlinx.coroutines.flow.*
import reactivecircus.flowbinding.android.widget.textChanges

/**
 * Keeps a reference to the last query from EditText
 */
object DistinctText {
    var text: String = ""
}

/**
 * checks that string emissions from an EditText for example,
 * are distinct
 */
val Flow<String>.checkDistinct: Flow<String>
    get() {
        return this.filter { string ->
            DistinctText.text != string
        }.onEach { value ->
            DistinctText.text = value
        }
    }

const val DEBOUNCE_PERIOD: Long = 300L

val EditText.textChanges: Flow<String>
    get() = this.textChanges()
        .drop(2)
        .debounce(DEBOUNCE_PERIOD)
        .map { it.toString() }
        .map { it.trim() }
        .conflate()
