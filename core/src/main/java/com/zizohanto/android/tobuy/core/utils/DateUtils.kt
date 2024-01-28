package com.zizohanto.android.tobuy.core.utils

import org.joda.time.Instant

object DateUtils {
    fun getCurrentTime(): Long {
        return Instant.now().millis
    }
}