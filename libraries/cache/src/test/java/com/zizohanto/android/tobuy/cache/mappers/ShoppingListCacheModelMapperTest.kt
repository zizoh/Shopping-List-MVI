package com.zizohanto.android.tobuy.cache.mappers

import com.google.common.truth.Truth.assertThat
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListCacheModel
import com.zizohanto.android.tobuy.cache.datafactory.CacheDataFactory.makeShoppingListEntity
import com.zizohanto.android.tobuy.cache.models.ShoppingListCacheModel
import com.zizohanto.android.tobuy.data.models.ShoppingListEntity
import com.zizohanto.android.tobuy.data.utils.DateUtils.getCurrentTime
import org.junit.Test

class ShoppingListCacheModelMapperTest {

    private val sut: ShoppingListCacheModelMapper = ShoppingListCacheModelMapper()

    @Test
    fun `mapToModel maps entity to model`() {
        val expected: ShoppingListEntity =
            makeShoppingListEntity().copy(name = "Vitsoe", dateModified = getCurrentTime())

        val actual: ShoppingListCacheModel = sut.mapToModel(expected)

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.name).isEqualTo(expected.name)
        assertThat(actual.budget).isEqualTo(expected.budget)
        assertThat(actual.dateCreated).isEqualTo(expected.dateCreated)
        assertThat(actual.dateModified).isEqualTo(expected.dateModified)
    }

    @Test
    fun `mapToEntity maps cache model to entity`() {
        val expected: ShoppingListCacheModel =
            makeShoppingListCacheModel().copy(name = "Vitsoe", dateModified = getCurrentTime())

        val actual: ShoppingListEntity = sut.mapToEntity(expected)

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.name).isEqualTo(expected.name)
        assertThat(actual.budget).isEqualTo(expected.budget)
        assertThat(actual.dateCreated).isEqualTo(expected.dateCreated)
        assertThat(actual.dateModified).isEqualTo(expected.dateModified)
    }
}