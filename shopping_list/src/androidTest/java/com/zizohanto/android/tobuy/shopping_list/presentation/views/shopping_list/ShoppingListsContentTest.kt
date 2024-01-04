package com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.zizohanto.android.tobuy.shopping_list.HiltTestActivity
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getShoppingListLoadedState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ShoppingListsContentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Test
    fun idleState_ToolbarIsDisplayed() {
        setShoppingListContent(ShoppingListViewState())

        composeTestRule.onNodeWithText("Shopping List").assertIsDisplayed()
    }

    @Test
    fun loadingState_ProgressBarIsDisplayed() {
        setShoppingListContent(ShoppingListViewState(isLoading = true))

        composeTestRule.onNodeWithTag("progressBar").assertIsDisplayed()
    }

    @Test
    fun loadedState_ShoppingListsAreDisplayed() {
        setShoppingListContent(getShoppingListLoadedState())

        composeTestRule.onNodeWithText("Vegetables").assertIsDisplayed()
    }

    @Test
    fun loadedState_FloatingButtonIsDisplayed() {
        setShoppingListContent(getShoppingListLoadedState())

        composeTestRule.onNodeWithContentDescription(
            "Add New Shopping List"
        ).assertIsDisplayed()
    }

    @Test
    fun emptyState_EmptyStateIsDisplayed() {
        setShoppingListContent(ShoppingListViewState())

        composeTestRule.onNodeWithText("No shopping list added yet.").assertIsDisplayed()
    }

    @Test
    fun emptyState_FloatingButtonIsDisplayed() {
        setShoppingListContent(ShoppingListViewState())

        composeTestRule.onNodeWithContentDescription(
            "Add New Shopping List"
        ).assertIsDisplayed()
    }

    @Test
    fun errorState_ErrorStateIsDisplayed() {
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