package com.zizohanto.android.tobuy.data.models

import com.zizohanto.android.tobuy.data.utils.DateUtils.getCurrentTime
import java.util.*

data class ProductEntity(
    val id: String = UUID.randomUUID().toString(),
    val shoppingListId: String,
    val name: String = "",
    val price: Double = 0.0,
    val dateAdded: Long = getCurrentTime()
)