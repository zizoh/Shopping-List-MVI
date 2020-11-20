package com.zizohanto.android.tobuy.cache.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.getRandomIntWithinRange
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeRandomString
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListCacheModels
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListWithProductsCacheModel
import com.zizohanto.android.tobuy.data.utils.DateUtils.getCurrentTime
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("PublicApiImplicitType")
@RunWith(AndroidJUnit4::class)
class ShoppingListDaoTest {

    private lateinit var sut: ShoppingListDao
    private lateinit var database: ShoppingListDatabase

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingListDatabase::class.java
        ).allowMainThreadQueries().build()

        sut = database.shoppingListDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insertShoppingList inserts shopping list`() = runBlocking {
        val expected: ShoppingListCacheModel = makeShoppingListCacheModel()

        sut.insertShoppingList(expected)

        val actual: ShoppingListCacheModel? = sut.getShoppingListWithId(expected.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `updateShoppingList updates shopping list`() = runBlocking {
        val shoppingList: ShoppingListCacheModel = makeShoppingListCacheModel()
        sut.insertShoppingList(shoppingList)
        val expected: ShoppingListCacheModel =
            shoppingList.copy(name = "Vistoe", dateModified = getCurrentTime())

        sut.updateShoppingList(expected.id, expected.name, expected.dateModified)

        val actual: ShoppingListCacheModel? = sut.getShoppingListWithId(shoppingList.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getShoppingListWithId returns null if shopping list does not exist`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListCacheModel> = makeShoppingListCacheModels(count)
        shoppingLists.forEach {
            sut.insertShoppingList(it)
        }

        val actual: ShoppingListCacheModel? = sut.getShoppingListWithId(makeRandomString())

        assertThat(actual).isEqualTo(null)
    }

    @Test
    fun `getShoppingListWithId returns shopping list`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListCacheModel> = makeShoppingListCacheModels(count)
        val expected: ShoppingListCacheModel = shoppingLists[getRandomIntWithinRange(0, count)]
        shoppingLists.forEach {
            sut.insertShoppingList(it)
        }

        val actual: ShoppingListCacheModel? = sut.getShoppingListWithId(expected.id)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getShoppingLists returns is empty if no shopping list has been saved`() = runBlocking {
        val actual: Boolean = sut.getShoppingLists().isEmpty()

        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `getShoppingLists returns all saved shopping lists`() = runBlocking {
        val expected: List<ShoppingListCacheModel> = makeShoppingListCacheModels(10)
        expected.forEach {
            sut.insertShoppingList(it)
        }

        val actual: List<ShoppingListWithProductsCacheModel> = sut.getShoppingLists()

        expected.forEachIndexed { index, shoppingListCacheModel ->
            assertThat(actual[index].shoppingList).isEqualTo(shoppingListCacheModel)
        }
    }

    @Test
    fun `getShoppingListWithProductsOrNull returns null if shopping list does not exist`() =
        runBlocking {
            val shoppingLists: List<ShoppingListCacheModel> = makeShoppingListCacheModels(10)
            shoppingLists.forEach {
                sut.insertShoppingList(it)
            }

            val actual: ShoppingListWithProductsCacheModel? = sut.getShoppingListWithProductsOrNull(
                makeRandomString()
            )

            assertThat(actual).isEqualTo(null)
        }

    @Test
    fun `getShoppingListWithProductsOrNull returns shopping list if shopping list exist`() =
        runBlocking {
            val count = 10
            val shoppingLists: List<ShoppingListCacheModel> = makeShoppingListCacheModels(count)
            shoppingLists.forEach {
                sut.insertShoppingList(it)
            }
            val expected: ShoppingListCacheModel = shoppingLists[getRandomIntWithinRange(0, count)]

            val actual: ShoppingListWithProductsCacheModel? = sut.getShoppingListWithProductsOrNull(
                expected.id
            )

            assertThat(actual?.shoppingList).isEqualTo(expected)
        }

    @Test
    fun `deleteShoppingList deletes shopping list`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListCacheModel> = makeShoppingListCacheModels(count)
        shoppingLists.forEach {
            sut.insertShoppingList(it)
        }
        val expectedSize: Int = shoppingLists.size - 1
        val shoppingList: ShoppingListCacheModel = shoppingLists[getRandomIntWithinRange(0, count)]

        sut.deleteShoppingList(shoppingList.id)

        val actual: ShoppingListWithProductsCacheModel? = sut.getShoppingListWithProductsOrNull(
            shoppingList.id
        )
        assertThat(actual?.shoppingList).isEqualTo(null)
        val actualSize: Int = sut.getShoppingLists().size
        assertThat(actualSize).isEqualTo(expectedSize)
    }

    @Test
    fun `deleteAllShoppingLists deletes all shopping lists`() = runBlocking {
        val count = 10
        val shoppingLists: List<ShoppingListCacheModel> = makeShoppingListCacheModels(count)
        shoppingLists.forEach {
            sut.insertShoppingList(it)
        }

        sut.deleteAllShoppingLists()

        val actual: Boolean = sut.getShoppingLists().isEmpty()
        assertThat(actual).isEqualTo(true)
    }
}