package com.zizohanto.android.tobuy.shopping_list.presentation.products

import androidx.lifecycle.SavedStateHandle
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductStateMachine
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.ProductViewModel
import com.zizohanto.android.tobuy.shopping_list.presentation.mvi.products.mvi.ProductsViewIntent
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getProductModel
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getRandomString
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getShoppingListModel
import com.zizohanto.android.tobuy.shopping_list.utilities.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val productStateMachine = mockk<ProductStateMachine>(relaxed = true)

    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        viewModel = ProductViewModel(productStateMachine, SavedStateHandle())
    }

    @Test
    fun testAddNewProductEmitsAddNewProductAtPosition() = runTest {
        val slot = slot<Flow<ProductsViewIntent>>()

        coEvery {
            productStateMachine.processIntents(capture(slot))
        } returns flowOf()

        val expectedProductPosition = 1
        viewModel.addNewProduct(shoppingListId = "1", expectedProductPosition)

        val addNewProductAtPosition =
            slot.captured.toList()
                .last() as ProductsViewIntent.ProductViewIntent.AddNewProductAtPosition
        assertEquals(expectedProductPosition, addNewProductAtPosition.newProductPosition)
    }

    @Test
    fun testAddNewProductEmitsAddNewProductToShoppingListWithID() = runTest {
        val slot = slot<Flow<ProductsViewIntent>>()

        coEvery {
            productStateMachine.processIntents(capture(slot))
        } returns flowOf()

        val expectedProductPosition = getRandomString()
        viewModel.addNewProduct(expectedProductPosition, newProductPosition = 0)

        val addNewProductAtPosition =
            slot.captured.toList()
                .last() as ProductsViewIntent.ProductViewIntent.AddNewProductAtPosition
        assertEquals(expectedProductPosition, addNewProductAtPosition.shoppingListId)
    }

    @Test
    fun testUpdateProductEmitsSaveProduct() = runTest {
        val slot = slot<Flow<ProductsViewIntent>>()

        coEvery {
            productStateMachine.processIntents(capture(slot))
        } returns flowOf()

        val expectedProduct = getProductModel()
        viewModel.updateProduct(expectedProduct)

        val saveProduct =
            slot.captured.toList().last() as ProductsViewIntent.ProductViewIntent.SaveProduct
        assertEquals(expectedProduct, saveProduct.product)
    }

    @Test
    fun testDeleteProductEmitsDeleteProduct() = runTest {
        val slot = slot<Flow<ProductsViewIntent>>()

        coEvery {
            productStateMachine.processIntents(capture(slot))
        } returns flowOf()

        val expectedProduct = getProductModel()
        viewModel.deleteProduct(expectedProduct)

        val deleteProduct =
            slot.captured.toList().last() as ProductsViewIntent.ProductViewIntent.DeleteProduct
        assertEquals(expectedProduct, deleteProduct.product)
    }

    @Test
    fun testUpdateShoppingListEmitsSaveShoppingList() = runTest {
        val slot = slot<Flow<ProductsViewIntent>>()

        coEvery {
            productStateMachine.processIntents(capture(slot))
        } returns flowOf()

        val expectedShoppingList = getShoppingListModel()
        viewModel.updateShoppingList(expectedShoppingList)

        val deleteProduct =
            slot.captured.toList().last() as ProductsViewIntent.ProductViewIntent.SaveShoppingList
        assertEquals(expectedShoppingList, deleteProduct.shoppingList)
    }
}