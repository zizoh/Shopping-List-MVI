package com.zizohanto.android.tobuy.utils

import org.joda.time.Instant

object DateUtils {
    fun getCurrentTime(): Long {
        return Instant.now().millis
    }
}