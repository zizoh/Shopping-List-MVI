package com.zizohanto.android.tobuy.cache.impl

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.getRandomIntWithinRange
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeRandomString
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListEntities
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListEntity
import com.zizohanto.android.tobuy.cache.mappers.ProductCacheModelMapper
import com.zizohanto.android.tobuy.cache.mappers.ShoppingListCacheModelMapper
import com.zizohanto.android.tobuy.cache.mappers.ShoppingListWithProductsCacheModelMapper
import com.zizohanto.android.tobuy.cache.room.ShoppingListDatabase
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.data.utils.DateUtils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("PublicApiImplicitType")
@RunWith(AndroidJUnit4::class)
class ShoppingListCacheImplTest {

    private lateinit var sut: ShoppingListCacheImpl

    private lateinit var database: ShoppingListDatabase

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingListDatabase::class.java
        ).allowMainThreadQueries().build()

        sut = ShoppingListCacheImpl(
            database.shoppingListDao,
            ShoppingListCacheModelMapper(),
            ShoppingListWithProductsCacheModelMapper(
                ShoppingListCacheModelMapper(),
                ProductCacheModelMapper()
            )
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `saveShoppingList saves shopping list`() = runBlocking {
        val expected: ShoppingListEntity = makeShoppingListEntity()

        sut.saveShoppingList(expected)

        val actual: ShoppingListEntity? = sut.getShoppingList(expected.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `updateShoppingList updates shopping list`() = runBlocking {
        val shoppingList: ShoppingListEntity = makeShoppingListEntity()
        sut.saveShoppingList(shoppingList)
        val expected: ShoppingListEntity =
            shoppingList.copy(name = "Vistoe", dateModified = DateUtils.getCurrentTime())

        sut.updateShoppingList(expected.id, expected.name, expected.dateModified)

        val actual: ShoppingListEntity? = sut.getShoppingList(shoppingList.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getShoppingList returns null if shopping list does not exist`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListEntity> = makeShoppingListEntities(count)
        shoppingLists.forEach {
            sut.saveShoppingList(it)
        }

        val actual: ShoppingListEntity? = sut.getShoppingList(makeRandomString())

        assertThat(actual).isEqualTo(null)
    }

    @Test
    fun `getShoppingList returns shopping list`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListEntity> = makeShoppingListEntities(count)
        val expected: ShoppingListEntity = shoppingLists[getRandomIntWithinRange(0, count)]
        shoppingLists.forEach {
            sut.saveShoppingList(it)
        }
        shoppingLists.forEach {
            sut.saveShoppingList(it)
        }

        val actual: ShoppingListEntity? = sut.getShoppingList(expected.id)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getShoppingListWithProductsOrNull returns null if shopping list does not exist`() =
        runBlocking {
            val count = 10
            val shoppingLists: List<ShoppingListEntity> = makeShoppingListEntities(count)
            shoppingLists.forEach {
                sut.saveShoppingList(it)
            }

            val actual: ShoppingListWithProductsEntity? =
                sut.getShoppingListWithProductsOrNull(makeRandomString())

            assertThat(actual).isEqualTo(null)
        }

    @Test
    fun `getShoppingListWithProductsOrNull returns shopping list if shopping list exist`() =
        runBlocking {
            val count = 10
            val shoppingLists: List<ShoppingListEntity> = makeShoppingListEntities(count)
            shoppingLists.forEach {
                sut.saveShoppingList(it)
            }
            val expected: ShoppingListEntity = shoppingLists[getRandomIntWithinRange(0, count)]

            val actual: ShoppingListWithProductsEntity? =
                sut.getShoppingListWithProductsOrNull(expected.id)

            assertThat(actual?.shoppingListEntity).isEqualTo(expected)
        }

    @Test
    fun `getAllShoppingLists is empty if no shopping list has been saved`() = runBlocking {
        val actual: Boolean = sut.getAllShoppingLists().isEmpty()

        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `getAllShoppingLists returns all saved shopping lists`() = runBlocking {
        val expected: List<ShoppingListEntity> = makeShoppingListEntities(10)
        expected.forEach {
            sut.saveShoppingList(it)
        }

        val actual: List<ShoppingListWithProductsEntity> = sut.getAllShoppingLists()

        expected.forEachIndexed { index, shoppingListCacheModel ->
            assertThat(actual[index].shoppingListEntity).isEqualTo(shoppingListCacheModel)
        }
    }

    @Test
    fun `deleteShoppingList deletes shopping list`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListEntity> = makeShoppingListEntities(count)
        shoppingLists.forEach {
            sut.saveShoppingList(it)
        }
        val expectedSize: Int = shoppingLists.size - 1
        val shoppingList: ShoppingListEntity = shoppingLists[getRandomIntWithinRange(0, count)]

        sut.deleteShoppingList(shoppingList.id)

        val actual: ShoppingListWithProductsEntity? = sut.getShoppingListWithProductsOrNull(
            shoppingList.id
        )
        assertThat(actual?.shoppingListEntity).isEqualTo(null)
        val actualSize: Int = sut.getAllShoppingLists().size
        assertThat(actualSize).isEqualTo(expectedSize)
    }

    @Test
    fun `deleteAllShoppingLists deletes all shopping lists`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListEntity> = makeShoppingListEntities(count)
        shoppingLists.forEach {
            sut.saveShoppingList(it)
        }

        sut.deleteAllShoppingLists()

        val actual: Boolean = sut.getAllShoppingLists().isEmpty()
        assertThat(actual).isEqualTo(true)
    }
}