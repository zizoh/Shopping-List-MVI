package com.zizohanto.android.tobuy.domain.utils

import org.joda.time.Instant

object DateUtils {
    fun getCurrentTime(): Long {
        return Instant.now().millis
    }
}