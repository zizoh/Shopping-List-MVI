package com.zizohanto.android.tobuy.shopping_list.ext

val Throwable.errorMessage: String
    get() = message ?: localizedMessage ?: "An error occurred"

fun <T> List<T>.replaceFirst(element: T, predicate: (T) -> Boolean): List<T> {
    val items: ArrayList<T> = ArrayList(this)
    for ((index, item) in items.withIndex()) {
        if (predicate(item)) {
            items[index] = element
            return items
        }
    }
    return items
}

fun <T> List<T>.removeFirst(predicate: (T) -> Boolean): List<T> {
    val items: ArrayList<T> = ArrayList(this)
    for ((index, item) in items.withIndex()) {
        if (predicate(item)) {
            items.removeAt(index)
            return items
        }
    }
    return items
}