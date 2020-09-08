package com.zizohanto.android.tobuy.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zizohanto.android.tobuy.cache.BuildConfig
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel

@Database(
    entities = [
        ProductCacheModel::class,
        ShoppingListCacheModel::class
    ],
    version = BuildConfig.databaseVersion,
    exportSchema = false
)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract val productDao: ProductDao

    abstract val shoppingListDao: ShoppingListDao

    companion object {
        private const val DATABASE_NAME: String = "shopping_list_db"
        fun build(context: Context): ShoppingListDatabase = Room.databaseBuilder(
            context.applicationContext,
            ShoppingListDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}