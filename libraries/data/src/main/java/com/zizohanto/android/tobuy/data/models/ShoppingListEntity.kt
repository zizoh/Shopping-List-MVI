package com.zizohanto.android.tobuy.data.models

import com.zizohanto.android.tobuy.data.utils.DateUtils.getCurrentTime
import java.util.*

data class ShoppingListEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val budget: Double = 0.0,
    val dateCreated: Long = getCurrentTime(),
    val dateModified: Long = getCurrentTime()
)