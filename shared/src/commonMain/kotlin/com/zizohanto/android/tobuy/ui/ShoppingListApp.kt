package com.zizohanto.android.tobuy.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.zizohanto.android.tobuy.presentation.views.product.ProductsScreen
import com.zizohanto.android.tobuy.presentation.views.shopping_list.ShoppingListsScreen

@Composable
fun ShoppingListApp(component: DefaultAppComponent) {
    Children(
        stack = component.stack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is AppComponent.Child.Products -> ProductsScreen(child.component)
            is AppComponent.Child.ShoppingList -> ShoppingListsScreen(child.component, false)
        }
    }
}