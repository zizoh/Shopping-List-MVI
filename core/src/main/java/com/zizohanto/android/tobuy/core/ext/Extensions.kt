package com.zizohanto.android.tobuy.core.ext

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