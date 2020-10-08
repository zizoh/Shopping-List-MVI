package com.zizohanto.android.tobuy.shopping_list.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingListModel(
    val id: String,
    val name: String,
    val budget: Double,
    val dateCreated: Long,
    val dateModified: Long
) : Parcelable