package com.zizohanto.android.tobuy.presentation.views.product

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.zizohanto.android.tobuy.presentation.mvi.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.ui.MainActivity
import com.zizohanto.android.tobuy.utilities.DataFactory.getProductsViewState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductsContentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Test
    fun loaded_ToolbarBackButtonIsDisplayed() {
        setProductContent(ProductsViewState())

        composeTestRule.onNodeWithContentDescription("Top bar back button").assertIsDisplayed()
    }

    @Test
    fun loaded_ShoppingListNameIsDisplayed() {
        setProductContent(getProductsViewState())

        composeTestRule.onNodeWithText("Weekend").assertIsDisplayed()
    }

    @Test
    fun loaded_ProductTitleIsDisplayed() {
        setProductContent(getProductsViewState())

        composeTestRule.onNodeWithText("Vegetables").assertIsDisplayed()
    }

    @Test
    fun loaded_AddProductButtonIsDisplayed() {
        setProductContent(getProductsViewState())

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