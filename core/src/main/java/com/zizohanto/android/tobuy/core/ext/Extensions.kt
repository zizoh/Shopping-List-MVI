package com.zizohanto.android.tobuy.core.ext

val Throwable.errorMessage: String
    get() = message ?: localizedMessage ?: "An error occurred"