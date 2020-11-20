package com.zizohanto.android.tobuy.cache.mappers

import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductCacheModels
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductEntities
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeRandomString
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListEntity
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.models.ShoppingListWithProductsCacheModel
import com.zizohanto.android.tobuy.data.models.ProductEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.models.ShoppingListWithProductsEntity
import com.zizohanto.android.tobuy.data.utils.DateUtils.getCurrentTime
import org.junit.Test

class ShoppingListWithProductsCacheModelMapperTest {

    private val sut: ShoppingListWithProductsCacheModelMapper =
        ShoppingListWithProductsCacheModelMapper(
            ShoppingListCacheModelMapper(),
            ProductCacheModelMapper()
        )

    @Test
    fun `mapToModel maps entity to model`() {
        val expectedProducts: List<ProductEntity> = makeProductEntities(
            10,
            makeRandomString(),
        )
        val expectedShoppingList: ShoppingListEntity = makeShoppingListEntity()
            .copy(name = "Vitsoe", dateModified = getCurrentTime())
        val expected = ShoppingListWithProductsEntity(expectedShoppingList, expectedProducts)

        val actual: ShoppingListWithProductsCacheModel = sut.mapToModel(expected)

        assertThat(actual.shoppingList.id).isEqualTo(expected.shoppingListEntity.id)
        assertThat(actual.shoppingList.name).isEqualTo(expected.shoppingListEntity.name)
        assertThat(actual.shoppingList.budget).isEqualTo(expected.shoppingListEntity.budget)
        assertThat(actual.shoppingList.dateCreated).isEqualTo(expected.shoppingListEntity.dateCreated)
        assertThat(actual.shoppingList.dateModified).isEqualTo(expected.shoppingListEntity.dateModified)
        actual.products.forEachIndexed { index, (id, shoppingListId, name, price, position) ->
            assertThat(id).isEqualTo(expectedProducts[index].id)
            assertThat(shoppingListId).isEqualTo(expectedProducts[index].shoppingListId)
            assertThat(name).isEqualTo(expectedProducts[index].name)
            assertThat(price).isEqualTo(expectedProducts[index].price)
            assertThat(position).isEqualTo(expectedProducts[index].position)
        }
    }

    @Test
    fun `mapToEntity maps cache model to entity`() {
        val expectedProducts: List<ProductCacheModel> = makeProductCacheModels(
            10,
            makeRandomString()
        )
        val expectedShoppingList: ShoppingListCacheModel = makeShoppingListCacheModel()
            .copy(name = "Vitsoe", dateModified = getCurrentTime())
        val expected = ShoppingListWithProductsCacheModel(expectedShoppingList, expectedProducts)

        val actual: ShoppingListWithProductsEntity = sut.mapToEntity(expected)

        assertThat(actual.shoppingListEntity.id).isEqualTo(expected.shoppingList.id)
        assertThat(actual.shoppingListEntity.name).isEqualTo(expected.shoppingList.name)
        assertThat(actual.shoppingListEntity.budget).isEqualTo(expected.shoppingList.budget)
        assertThat(actual.shoppingListEntity.dateCreated).isEqualTo(expected.shoppingList.dateCreated)
        assertThat(actual.shoppingListEntity.dateModified).isEqualTo(expected.shoppingList.dateModified)
        actual.productEntities.forEachIndexed { index, (id, shoppingListId, name, price, position) ->
            assertThat(id).isEqualTo(expectedProducts[index].id)
            assertThat(shoppingListId).isEqualTo(expectedProducts[index].shoppingListId)
            assertThat(name).isEqualTo(expectedProducts[index].name)
            assertThat(price).isEqualTo(expectedProducts[index].price)
            assertThat(position).isEqualTo(expectedProducts[index].position)
        }
    }
}