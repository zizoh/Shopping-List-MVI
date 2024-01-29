package com.zizohanto.android.tobuy.shopping_list.utils

import org.joda.time.Instant

object DateUtils {
    fun getCurrentTime(): Long {
        return Instant.now().millis
    }
}