@file:OptIn(ExperimentalMaterial3Api::class)

package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductViewModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState
import com.zizohanto.android.tobuy.shopping_list.ui.theme.ShoppingListTheme

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {}
) {
    val state by viewModel.viewState.collectAsState(initial = ProductsViewState.Idle)
    ProductsContent(
        state,
        modifier,
        onBackPressed,
        onUpdateShoppingList = {
            viewModel.updateShoppingList(it)
        },
        onAddNewProduct = { shoppingListId, newProductPosition ->
            viewModel.addNewProduct(shoppingListId, newProductPosition)
        },
        onUpdateProduct = {
            viewModel.updateProduct(it)
        },
        onDeleteProduct = {
            viewModel.deleteProduct(it)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductsContent(
    state: ProductsViewState,
    modifier: Modifier,
    onBackPressed: () -> Unit,
    onUpdateShoppingList: (ProductsViewItem.ShoppingListModel) -> Unit,
    onAddNewProduct: (String, Int) -> Unit,
    onUpdateProduct: (ProductsViewItem.ProductModel) -> Unit,
    onDeleteProduct: (ProductsViewItem.ProductModel) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Top bar back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        (state as? ProductViewState.Success)?.let {
            Column(
                modifier = modifier.padding(innerPadding)
            ) {
                val listWithProducts = it.listWithProducts
                val shoppingList = listWithProducts.shoppingList
                val products = listWithProducts.products
                ShoppingListTitle(
                    shoppingList,
                    onUpdateShoppingList = onUpdateShoppingList,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn {
                    items(products) { product ->
                        Divider(color = MaterialTheme.colorScheme.outlineVariant)
                        RowProduct(
                            product = product,
                            onAddNewProduct = onAddNewProduct,
                            onUpdateProduct = onUpdateProduct,
                            onDeleteProduct = onDeleteProduct
                        )
                        Divider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AddProductButton(Modifier.padding(start = 16.dp)) {
                    onAddNewProduct.invoke(shoppingList.id, products.size)
                }
            }
        }
    }
}

@Composable
fun ShoppingListTitle(
    shoppingList: ProductsViewItem.ShoppingListModel,
    onUpdateShoppingList: (ProductsViewItem.ShoppingListModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var shoppingListTitle by rememberSaveable { mutableStateOf(shoppingList.name) }
    TextField(
        value = shoppingListTitle,
        placeholder = {
            Text(stringResource(R.string.title))
        },
        textStyle = MaterialTheme.typography.titleMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier,
        onValueChange = {
            shoppingListTitle = it
            onUpdateShoppingList.invoke(shoppingList.copy(name = shoppingListTitle))
        }
    )
}

@Composable
fun RowProduct(
    product: ProductsViewItem.ProductModel,
    onAddNewProduct: (String, Int) -> Unit,
    onUpdateProduct: (ProductsViewItem.ProductModel) -> Unit,
    onDeleteProduct: (ProductsViewItem.ProductModel) -> Unit
) {
    var productName by rememberSaveable { mutableStateOf(product.name) }
    var isRemoveButtonVisible by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = productName,
            placeholder = {
                Text(stringResource(R.string.product))
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                productName = it
                onUpdateProduct.invoke(product.copy(name = productName))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (product.name.isNotEmpty()) {
                        onAddNewProduct.invoke(product.shoppingListId, product.position)
                    }
                },
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .onFocusChanged { focusState ->
                    isRemoveButtonVisible = focusState.hasFocus
                }
        )
        if (isRemoveButtonVisible) {
            IconButton(
                onClick = {
                    onDeleteProduct.invoke(product)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.cont_desc_remove_button)
                )
            }
        }
    }
}

@Composable
fun AddProductButton(
    modifier: Modifier = Modifier,
    onAddProductClick: () -> Unit
) {
    ElevatedButton(
        shape = RoundedCornerShape(8.dp),
        onClick = onAddProductClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.add_product),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ProductsViewPreview() {
    val shoppingList = ProductsViewItem.ShoppingListModel("", "Weekend", 0.0, 0L, 0L)
    val product = ProductsViewItem.ProductModel("", "", "Vegetables", 19.59, 1)
    val state = ProductViewState.Success(
        ShoppingListWithProductsModel(shoppingList, listOf(product))
    )
    ShoppingListTheme {
        ProductsContent(
            state,
            modifier = Modifier,
            onBackPressed = {},
            {},
            { _, _ -> },
            {},
            {},
        )
    }
}
