package com.zizohanto.android.tobuy.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.zizohanto.android.tobuy.presentation.mvi.products.ProductsComponent
import com.zizohanto.android.tobuy.presentation.mvi.shopping_list.ShoppingListComponent
import kotlinx.serialization.Serializable

interface AppComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class ShoppingList(val component: ShoppingListComponent) : Child()
        class Products(val component: ProductsComponent) : Child()
    }
}

class DefaultAppComponent(
    componentContext: ComponentContext
) : AppComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AppComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.ShoppingList,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): AppComponent.Child =
        when (config) {
            is Config.ShoppingList -> {
                AppComponent.Child.ShoppingList(
                    ShoppingListComponent(componentContext) { shoppingListId ->
                        navigation.push(Config.Products(shoppingListId))
                    }
                )
            }
            is Config.Products -> {
                AppComponent.Child.Products(
                    ProductsComponent(
                        componentContext,
                        config.shoppingListId
                    ) {
                        navigation.pop {
                            val shoppingList =
                                stack.active.instance as? AppComponent.Child.ShoppingList
                            shoppingList?.component?.loadShoppingLists()
                        }
                    }
                )
            }
        }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object ShoppingList : Config

        @Serializable
        data class Products(val shoppingListId: String) : Config
    }
}