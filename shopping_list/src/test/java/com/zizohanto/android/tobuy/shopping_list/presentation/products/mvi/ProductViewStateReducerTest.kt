package com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi

import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ProductModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListModelMapper
import com.zizohanto.android.tobuy.shopping_list.presentation.mappers.ShoppingListWithProductsModelMapper
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getResultProductAddedAtPosition
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductViewStateReducerTest {

    private val listWithProductsMapper  = mockk<ShoppingListWithProductsModelMapper>()
    private val shoppingListModelMapper = mockk<ShoppingListModelMapper>()
    private val productMapper = mockk<ProductModelMapper>()

    private val reducer = ProductViewStateReducer(
        listWithProductsMapper,
        shoppingListModelMapper,
        productMapper
    )

    @Test
    fun `productAddedAtPosition adds product to empty list`() {
        val newProduct = DataFactory.getProductModel()
        val previousState = ProductsViewState(DataFactory.getShoppingLists(emptyList()))

        every { productMapper.mapToModel(any()) } returns newProduct

        val newState = reducer.reduce(previousState, getResultProductAddedAtPosition())

        assertTrue(newState.listWithProducts?.products!!.size == 1)
        with(newState.listWithProducts?.products!!.first()) {
            assertEquals(newProduct.id, id)
            assertEquals(newProduct.name, name)
            assertEquals(newProduct.price, price, 0.0)
            assertEquals(newProduct.position, position)
        }
    }

    @Test
    fun `productAddedAtPosition adds product at specified position in non-empty list`() {
        val expectedNewProductPosition = 1
        val newProduct = DataFactory.getProductModel().copy(position = expectedNewProductPosition)
        every { productMapper.mapToModel(any()) } returns newProduct

        val previousState = ProductsViewState(DataFactory.getShoppingLists(2))

        val newState = reducer.reduce(previousState, getResultProductAddedAtPosition())

        assertTrue(newState.listWithProducts?.products!!.size == 3)
        with(newState.listWithProducts?.products?.get(expectedNewProductPosition)!!) {
            assertEquals(newProduct.id, id)
            assertEquals(newProduct.name, name)
            assertEquals(newProduct.price, price, 0.0)
            assertEquals(newProduct.position, expectedNewProductPosition)
        }
    }

    @Test
    fun `productAddedAtPosition adds product at end of list if position is out of bounds in non-empty list`() {
        val expectedNewProductPosition = 5
        val newProduct = DataFactory.getProductModel().copy(position = expectedNewProductPosition)
        every { productMapper.mapToModel(any()) } returns newProduct

        val previousState = ProductsViewState(DataFactory.getShoppingLists(2))

        val newState = reducer.reduce(previousState, getResultProductAddedAtPosition())

        assertTrue(newState.listWithProducts?.products!!.size == 3)
        with(newState.listWithProducts?.products?.last()!!) {
            assertEquals(newProduct.id, id)
            assertEquals(newProduct.name, name)
            assertEquals(newProduct.price, price, 0.0)
            assertEquals(newProduct.position, expectedNewProductPosition)
        }
    }
}