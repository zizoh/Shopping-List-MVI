package com.zizohanto.android.tobuy.domain.models

data class ShoppingList(
    val id: String,
    val name: String,
    val budget: Double,
    val dateCreated: Long,
    val dateModified: Long
)