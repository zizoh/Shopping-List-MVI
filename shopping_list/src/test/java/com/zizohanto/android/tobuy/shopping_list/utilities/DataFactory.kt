package com.zizohanto.android.tobuy.shopping_list.utilities

import kotlin.random.Random

object DataFactory {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getRandomString(length: Int = 5) = (1..length)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")
}