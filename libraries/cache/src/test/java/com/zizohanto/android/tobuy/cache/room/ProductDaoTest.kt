package com.zizohanto.android.tobuy.cache.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.getRandomIntWithinRange
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductCacheModel
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductCacheModels
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeRandomString
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("PublicApiImplicitType")
@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var sut: ProductDao
    private lateinit var database: ShoppingListDatabase

    private val shoppingList = CacheDataFactory.makeShoppingListCacheModel()

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingListDatabase::class.java
        ).allowMainThreadQueries().build()

        sut = database.productDao
        database.shoppingListDao.insertShoppingList(shoppingList)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insertProduct inserts product`() = runBlocking {
        val shoppingListId = shoppingList.id
        val expected: ProductCacheModel = makeProductCacheModel(shoppingListId)

        sut.insertProduct(expected)

        val actual: ProductCacheModel? = sut.getProductWithId(expected.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `updateProduct updates product`() = runBlocking {
        val shoppingListId = shoppingList.id
        val expected: ProductCacheModel = makeProductCacheModel(shoppingListId)
        sut.insertProduct(expected)

        val expectedName = "606 Universal Shelving System"
        val expectedPosition = getRandomIntWithinRange(0, 10)
        sut.updateProduct(expected.copy(name = expectedName, position = expectedPosition))

        val actual: ProductCacheModel? = sut.getProductWithId(expected.id)
        assertThat(actual?.name).isEqualTo(expectedName)
        assertThat(actual?.position).isEqualTo(expectedPosition)
    }

    @Test
    fun `insertProducts inserts products`() = runBlocking {
        val shoppingListId = shoppingList.id
        val expectedProducts: List<ProductCacheModel> = makeProductCacheModels(10, shoppingListId)

        sut.insertProducts(expectedProducts)

        val actualProducts: List<ProductCacheModel> = sut.getProducts(shoppingListId)
        assertThat(actualProducts).isEqualTo(expectedProducts)
    }

    @Test
    fun `getProductWithId returns null if product does not exist`() = runBlocking {
        val actual: ProductCacheModel? = sut.getProductWithId(makeRandomString())

        assertThat(actual).isEqualTo(null)
    }

    @Test
    fun `getProductWithId returns product if it exists`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        val expected: ProductCacheModel = products[getRandomIntWithinRange(0, count)]

        sut.insertProducts(products)

        val actual: ProductCacheModel? = sut.getProductWithId(expected.id)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getProducts returns empty list if no products have been saved`() = runBlocking {
        val shoppingListId = shoppingList.id

        val actual: Boolean = sut.getProducts(shoppingListId).isEmpty()

        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `getProducts returns products if products have been saved`() = runBlocking {
        val shoppingListId = shoppingList.id
        val expectedProducts: List<ProductCacheModel> = makeProductCacheModels(10, shoppingListId)
        sut.insertProducts(expectedProducts)

        val actualProducts: List<ProductCacheModel> = sut.getProducts(shoppingListId)

        assertThat(actualProducts).isEqualTo(expectedProducts)
    }

    @Test
    fun `getProductAtPosition returns null if product does not exist`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        sut.insertProducts(products)

        val position: Int = count + getRandomIntWithinRange(0, count)
        val actual: ProductCacheModel? = sut.getProductAtPosition(position, shoppingListId)

        assertThat(actual).isEqualTo(null)
    }

    @Test
    fun `getProductAtPosition returns product at position if it exists`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        val position = getRandomIntWithinRange(0, count)
        val expected: ProductCacheModel = products[position]
        sut.insertProducts(products)

        val actual: ProductCacheModel? = sut.getProductAtPosition(position, shoppingListId)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `productExists returns false if product does not exist`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        sut.insertProducts(products)

        val actual: Boolean = sut.productExists(makeRandomString())

        assertThat(actual).isEqualTo(false)
    }

    @Test
    fun `productExists returns true if product exists`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        val position = getRandomIntWithinRange(0, count)
        val product: ProductCacheModel = products[position]
        sut.insertProducts(products)

        val actual: Boolean = sut.productExists(product.id)

        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `deleteProduct deletes product`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        val position = getRandomIntWithinRange(0, count)
        val product: ProductCacheModel = products[position]
        val expectedSize: Int = products.size - 1
        sut.insertProducts(products)

        sut.deleteProduct(product.id)

        val actual: Boolean = sut.productExists(product.id)
        assertThat(actual).isEqualTo(false)
        val actualSize: Int = sut.getProducts(shoppingListId).size
        assertThat(actualSize).isEqualTo(expectedSize)
    }

    @Test
    fun `deleteAllProducts deletes all products`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        sut.insertProducts(products)

        sut.deleteAllProducts()

        val actual: Boolean = sut.getProducts(shoppingListId).isEmpty()
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `getLastPosition returns position of last product`() = runBlocking {
        val count = 10
        val shoppingListId = shoppingList.id
        val products: List<ProductCacheModel> = makeProductCacheModels(count, shoppingListId)
        val expected: Int = products.last().position
        sut.insertProducts(products)

        val actual: Int? = sut.getLastPosition(shoppingListId)

        assertThat(actual).isEqualTo(expected)
    }
}