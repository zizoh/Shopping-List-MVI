package com.zizohanto.android.tobuy.cache.mappers

import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductCacheModel
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeProductEntity
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeRandomString
import com.zizohanto.android.tobuy.cache.models.ProductCacheModel
import com.zizohanto.android.tobuy.data.models.ProductEntity
import org.junit.Test

class ProductCacheModelMapperTest {

    private val sut: ProductCacheModelMapper = ProductCacheModelMapper()

    @Test
    fun `mapToModel maps entity to model`() {
        val expected: ProductEntity = makeProductEntity(
            makeRandomString(),
            6
        ).copy(name = "606 Universal Shelving System")

        val actual: ProductCacheModel = sut.mapToModel(expected)

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.shoppingListId).isEqualTo(expected.shoppingListId)
        assertThat(actual.name).isEqualTo(expected.name)
        assertThat(actual.price).isEqualTo(expected.price)
        assertThat(actual.position).isEqualTo(expected.position)
    }

    @Test
    fun `mapToEntity maps cache model to entity`() {
        val expected: ProductCacheModel = makeProductCacheModel(
            makeRandomString(),
            6
        ).copy(name = "606 Universal Shelving System")

        val actual: ProductEntity = sut.mapToEntity(expected)

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.shoppingListId).isEqualTo(expected.shoppingListId)
        assertThat(actual.name).isEqualTo(expected.name)
        assertThat(actual.price).isEqualTo(expected.price)
        assertThat(actual.position).isEqualTo(expected.position)
    }
}