package com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list

import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewStateMachine
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getRandomString
import com.zizohanto.android.tobuy.shopping_list.utilities.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val shoppingListStateMachine = mockk<ShoppingListViewStateMachine>(relaxed = true)

    private lateinit var viewModel: ShoppingListViewModel

    @Before
    fun setUp() {
        viewModel = ShoppingListViewModel(shoppingListStateMachine)
    }

    @Test
    fun testOnListDeletedEmitsDeleteShoppingListWithCorrectID() = runTest {
        val slot = slot<Flow<ShoppingListViewIntent>>()

        coEvery {
            shoppingListStateMachine.processIntents(capture(slot))
        } returns flowOf()

        val expectedId = getRandomString()
        viewModel.onListDeleted(expectedId)

        val deleteShoppingList: ShoppingListViewIntent.DeleteShoppingList =
            slot.captured.toList().last() as ShoppingListViewIntent.DeleteShoppingList
        assertEquals(expectedId, deleteShoppingList.shoppingListId)
    }

    @Test
    fun testOnCreateShoppingListEmitsCreateNewShoppingList() = runTest {
        val slot = slot<Flow<ShoppingListViewIntent>>()

        coEvery {
            shoppingListStateMachine.processIntents(capture(slot))
        } returns flowOf()

        viewModel.onCreateShoppingList()

        assertTrue(slot.captured.toList().last() is ShoppingListViewIntent.CreateNewShoppingList)
    }

    @Test
    fun testLoadShoppingListsEmitsLoadShoppingList() = runTest {
        val slot = slot<Flow<ShoppingListViewIntent>>()

        coEvery {
            shoppingListStateMachine.processIntents(capture(slot))
        } returns flowOf()

        viewModel.loadShoppingLists()

        assertTrue(slot.captured.toList().last() is ShoppingListViewIntent.LoadShoppingLists)
    }

}