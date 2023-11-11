package com.zizohanto.android.tobuy.shopping_list.ui.products

import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem

interface ProductViewListener {
    fun onProductEdit(product: ProductsViewItem.ProductModel)
    fun onProductDelete(product: ProductsViewItem.ProductModel)
    fun onAddNewProduct(shoppingListId: String, newProductPosition: Int)
    fun onShoppingListEdit(shoppingList: ProductsViewItem.ShoppingListModel)
}
