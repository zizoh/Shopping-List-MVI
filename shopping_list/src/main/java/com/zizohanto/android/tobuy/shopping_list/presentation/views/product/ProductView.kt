package com.zizohanto.android.tobuy.shopping_list.presentation.views.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.products.ProductViewModel
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.products.mvi.ProductsViewState.ProductViewState

@Composable
fun ProductsView(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(),
    onBackPressed: () -> Unit = {}
) {
    val state by viewModel.viewState.collectAsState(initial = ProductsViewState.Idle)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = colorResource(R.color.amber_primary),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Top bar back button",
                            tint = colorResource(R.color.black)
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
                    viewModel,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn {
                    items(products) {
                        RowProduct(it, viewModel)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AddProductButton(
                    shoppingList.id,
                    products.size,
                    viewModel,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ShoppingListTitle(
    shoppingList: ProductsViewItem.ShoppingListModel,
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    var shoppingListTitle by rememberSaveable { mutableStateOf(shoppingList.name) }
    TextField(
        value = shoppingListTitle,
        textStyle = MaterialTheme.typography.subtitle2,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.amber_light),
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = colorResource(R.color.amber_light)
        ),
        modifier = modifier,
        onValueChange = {
            val title = it.trim()
            shoppingListTitle = title
            viewModel.updateShoppingList(shoppingList.copy(name = shoppingListTitle))
        }
    )
}

@Composable
fun RowProduct(
    product: ProductsViewItem.ProductModel,
    viewModel: ProductViewModel
) {
    var productName by rememberSaveable { mutableStateOf(product.name) }
    var isRemoveButtonVisible by rememberSaveable { mutableStateOf(true) }
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = productName,
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.amber_light),
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(R.color.amber_light)
            ),
            onValueChange = {
                val name = it.trim()
                productName = name
                viewModel.updateProduct(product.copy(name = productName))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (product.name.isNotEmpty()) {
                        viewModel.addNewProduct(product.shoppingListId, product.position)
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
                    viewModel.deleteProduct(product)
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
    shoppingListId: String,
    newProductPosition: Int,
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            viewModel.addNewProduct(shoppingListId, newProductPosition)
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.add_product),
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
