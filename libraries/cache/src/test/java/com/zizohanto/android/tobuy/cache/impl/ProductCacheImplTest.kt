package com.zizohanto.android.tobuy.cache.impl

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.getRandomIntWithinRange
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductEntities
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeRandomString
import com.zizohanto.android.tobuy.cache.mappers.ProductCacheModelMapper
import com.zizohanto.android.tobuy.cache.room.ShoppingListDatabase
import com.zizohanto.android.tobuy.data.models.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("PublicApiImplicitType")
@RunWith(AndroidJUnit4::class)
class ProductCacheImplTest {

    private lateinit var sut: ProductCacheImpl

    private lateinit var database: ShoppingListDatabase

    private val shoppingList = CacheDataFactory.makeShoppingListCacheModel()

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingListDatabase::class.java
        ).allowMainThreadQueries().build()

        sut = ProductCacheImpl(
            database.productDao,
            ProductCacheModelMapper()
        )
        database.shoppingListDao.insertShoppingList(shoppingList)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `saveProduct saves product`() = runBlocking {
        val shoppingListId = shoppingList.id
        val expected: ProductEntity = CacheDataFactory.makeProductEntity(shoppingListId, 0)

        sut.saveProduct(expected)

        val actual: ProductEntity = sut.getProducts(shoppingListId).first()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `makeNewProduct adds product at position 0 if no products exist before`() =
        runBlocking {
            val shoppingListId = shoppingList.id

            val actual: Int = sut.makeNewProduct(shoppingListId).position

            assertThat(actual).isEqualTo(0)
        }

    @Test
    fun `makeNewProduct adds product at the bottom of existing list`() =
        runBlocking {
            val count = 10
            val size = getRandomIntWithinRange(0, count)
            val shoppingListId = shoppingList.id
            val products: MutableList<ProductEntity> =
                makeProductEntities(size, shoppingListId).toMutableList()
            products.forEach {
                sut.saveProduct(it)
            }
            val expectedSize = products.lastIndex + 1

            val actual: Int = sut.makeNewProduct(shoppingListId).position

            assertThat(actual).isEqualTo(expectedSize)
        }

    @Test
    fun `getProducts returns products`() = runBlocking {
        val count = 10
        val size = getRandomIntWithinRange(0, count)
        val shoppingListId = shoppingList.id
        val expectedProducts: MutableList<ProductEntity> =
            makeProductEntities(size, shoppingListId).toMutableList()
        expectedProducts.forEach {
            sut.saveProduct(it)
        }

        val actualProducts: List<ProductEntity> = sut.getProducts(shoppingListId)

        assertThat(actualProducts).isEqualTo(expectedProducts)
    }

    @Test
    fun `deleteProduct deletes product`() = runBlocking {
        val count = 10
        val position = getRandomIntWithinRange(0, count)
        val productId = makeRandomString()
        val shoppingListId = shoppingList.id
        val products: MutableList<ProductEntity> =
            makeProductEntities(count, shoppingListId).toMutableList()
        products[position] = products[position].copy(id = productId)
        products.forEach {
            sut.saveProduct(it)
        }
        val expectedSize = products.size - 1

        sut.deleteProduct(products[position])

        val actual: Int = sut.getProducts(shoppingListId).size
        assertThat(actual).isEqualTo(expectedSize)
    }

    @Test
    fun `deleteProduct updates position of remaining products`() = runBlocking {
        val count = 10
        val position = getRandomIntWithinRange(0, count)
        val productId = makeRandomString()
        val shoppingListId = shoppingList.id
        val products: MutableList<ProductEntity> =
            makeProductEntities(count, shoppingListId).toMutableList()
        products[position] = products[position].copy(id = productId)
        products.forEach {
            sut.saveProduct(it)
        }

        sut.deleteProduct(products[position])

        val actual: List<ProductEntity> = sut.getProducts(shoppingListId)
        actual.forEachIndexed { index, productEntity ->
            assertThat(productEntity.position).isEqualTo(index)
        }
    }

    @Test
    fun `deleteAllProducts deletes all products`() = runBlocking {
        val shoppingListId = shoppingList.id
        val products: List<ProductEntity> = makeProductEntities(10, shoppingListId)
        products.forEach {
            sut.saveProduct(it)
        }

        sut.deleteAllProducts()

        val actual: Boolean = sut.getProducts(shoppingListId).isEmpty()
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `makeNewProductAtPosition adds new product at position`() = runBlocking {
        val count = 10
        val position = getRandomIntWithinRange(0, count)
        val shoppingListId = shoppingList.id
        val products: MutableList<ProductEntity> =
            makeProductEntities(count, shoppingListId).toMutableList()
        products.forEach {
            sut.saveProduct(it)
        }
        val expectedSize = products.size + 1

        sut.makeNewProductAtPosition(shoppingListId, position)

        val actual: List<ProductEntity> = sut.getProducts(shoppingListId)
        assertThat(actual.size).isEqualTo(expectedSize)
        actual.forEachIndexed { index, productEntity ->
            if (index == position) {
                assertThat(!products.contains(productEntity))
            } else {
                assertThat(products.contains(productEntity))
            }
        }
    }

    @Test
    fun `makeNewProductAtPosition updates position of existing products`() = runBlocking {
        val count = 10
        val position = getRandomIntWithinRange(0, count)
        val shoppingListId = shoppingList.id
        val products: MutableList<ProductEntity> =
            makeProductEntities(count, shoppingListId).toMutableList()
        products.forEach {
            sut.saveProduct(it)
        }

        sut.makeNewProductAtPosition(shoppingListId, position)

        val actual: List<ProductEntity> = sut.getProducts(shoppingListId)
        actual.forEachIndexed { index, productEntity ->
            assertThat(productEntity.position).isEqualTo(index)
        }
    }
}