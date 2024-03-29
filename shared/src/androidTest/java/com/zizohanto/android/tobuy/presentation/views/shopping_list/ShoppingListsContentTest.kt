package com.zizohanto.android.tobuy.presentation.views.shopping_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.mvi.ShoppingListViewState
import com.zizohanto.android.tobuy.ui.MainActivity
import com.zizohanto.android.tobuy.utilities.DataFactory.getShoppingListStateWithList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ShoppingListsContentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Test
    fun loadedState_ToolbarIsDisplayed() {
        setShoppingListContent(ShoppingListViewState())

        composeTestRule.onNodeWithText("Shopping List").assertIsDisplayed()
    }

    @Test
    fun isLoading_ProgressBarIsDisplayed() {
        setShoppingListContent(ShoppingListViewState(isLoading = true))

        composeTestRule.onNodeWithTag("progressBar").assertIsDisplayed()
    }

    @Test
    fun shoppingListNotEmpty_ShoppingListsAreDisplayed() {
        setShoppingListContent(getShoppingListStateWithList())

        composeTestRule.onNodeWithText("Vegetables").assertIsDisplayed()
    }

    @Test
    fun shoppingListNotEmpty_FloatingButtonIsDisplayed() {
        setShoppingListContent(getShoppingListStateWithList())

        composeTestRule.onNodeWithContentDescription(
            "Add New Shopping List"
        ).assertIsDisplayed()
    }

    @Test
    fun shoppingListIsEmpty_EmptyStateIsDisplayed() {
        setShoppingListContent(ShoppingListViewState())

        composeTestRule.onNodeWithText("No shopping list added yet.").assertIsDisplayed()
    }

    @Test
    fun shoppingListIsEmpty_FloatingButtonIsDisplayed() {
        setShoppingListContent(ShoppingListViewState())

        composeTestRule.onNodeWithContentDescription(
            "Add New Shopping List"
        ).assertIsDisplayed()
    }

    @Test
    fun isError_ErrorStateIsDisplayed() {
        val errorMessage = "There was an error getting shopping lists"
        setShoppingListContent(ShoppingListViewState(error = errorMessage))

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    private fun setShoppingListContent(state: ShoppingListViewState) {
        composeTestRule.setContent {
            ShoppingListsContent(
                state = state,
                callbacks = ShoppingListsContentCallbacks({}, {}, {}, {})
            )
        }
    }
}