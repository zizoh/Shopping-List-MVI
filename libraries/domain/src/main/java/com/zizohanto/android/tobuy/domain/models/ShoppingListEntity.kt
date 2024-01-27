package com.zizohanto.android.tobuy.domain.models

import com.zizohanto.android.tobuy.domain.utils.DateUtils.getCurrentTime
import java.util.UUID

data class ShoppingListEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val budget: Double = 0.0,
    val dateCreated: Long = getCurrentTime(),
    val dateModified: Long = getCurrentTime()
)