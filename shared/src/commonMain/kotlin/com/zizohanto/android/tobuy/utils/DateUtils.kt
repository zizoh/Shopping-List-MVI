package com.zizohanto.android.tobuy.utils

import kotlinx.datetime.Clock

object DateUtils {
    fun getCurrentTime(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
}