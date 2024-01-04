package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.zizohanto.android.tobuy.shopping_list.HiltTestActivity
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.utilities.DataFactory.getProductsSuccess
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductsContentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Test
    fun idleState_ToolbarBackButtonIsDisplayed() {
        setProductContent(ProductsViewState())

        composeTestRule.onNodeWithContentDescription("Top bar back button").assertIsDisplayed()
    }

    @Test
    fun success_ShoppingListNameIsDisplayed() {
        setProductContent(getProductsSuccess())

        composeTestRule.onNodeWithText("Weekend").assertIsDisplayed()
    }

    @Test
    fun success_ProductTitleIsDisplayed() {
        setProductContent(getProductsSuccess())

        composeTestRule.onNodeWithText("Vegetables").assertIsDisplayed()
    }

    @Test
    fun success_AddProductButtonIsDisplayed() {
        setProductContent(getProductsSuccess())

        composeTestRule.onNodeWithText("Add product").assertIsDisplayed()
    }

    private fun setProductContent(state: ProductsViewState) {
        composeTestRule.setContent {
            ProductsContent(
                state,
                ProductsContentCallbacks({}, {}, { _, _ -> }, {}, {}),
            )
        }
    }
}