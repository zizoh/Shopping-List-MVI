@file:OptIn(ExperimentalFoundationApi::class)

package com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zizohanto.android.tobuy.shopping_list.ui.theme.ShoppingListTheme
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.views.EmptyStateView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListsScreen(
    state: ShoppingListViewState,
    listCLick: (String) -> Unit,
    listDelete: (String) -> Unit,
    create: () -> Unit,
    retry: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.shopping_list_frag_toolbar_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Box(Modifier.weight(1f)) {
                when (state) {
                    ShoppingListViewState.Idle -> {
                    }
                    ShoppingListViewState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                    is ShoppingListViewState.ShoppingListLoaded -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp)
                        ) {
                            items(state.listWithProducts) { item ->
                                ShoppingListItem(
                                    item,
                                    listCLick,
                                    listDelete,
                                    Modifier.padding(4.dp)
                                )
                            }
                        }
                        ShoppingListFloatingActionButton(create)
                    }
                    ShoppingListViewState.ShoppingListEmpty -> {
                        EmptyStateView(
                            stringResource(R.string.no_data),
                            "",
                            R.drawable.empty_basket,
                            shouldShowButton = false,
                            modifier = Modifier.fillMaxSize()
                        ) {}
                        ShoppingListFloatingActionButton(create)
                    }
                    is ShoppingListViewState.Error -> {
                        EmptyStateView(
                            stringResource(R.string.an_error_occurred),
                            state.message,
                            R.drawable.error,
                            shouldShowButton = true,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            retry.invoke()
                        }
                    }
                    is ShoppingListViewState.NewShoppingListLoaded -> {
                        state.openProductScreen.consume(listCLick::invoke)
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListTitle(
    shoppingListTitle: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = shoppingListTitle,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

@Composable
fun ProductItem(
    productName: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_check_box_outline_blank_24),
            contentDescription = stringResource(id = R.string.cont_desc_select_button),
        )
        Text(
            text = productName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListItem(
    listWithProducts: ShoppingListWithProductsModel,
    onClick: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shoppingList = listWithProducts.shoppingList
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onClick.invoke(shoppingList.id)
                },
                onLongClick = {
                    showDialog = true
                }
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        ShoppingListTitle(
            shoppingList.name,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        listWithProducts.products.forEach {
            ProductItem(it.name)
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.delete_shopping_list)) },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete.invoke(shoppingList.id)
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun BoxScope.ShoppingListFloatingActionButton(create: () -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.Companion
            .align(Alignment.BottomEnd)
            .navigationBarsPadding()
            .padding(24.dp),
        onClick = {
            create.invoke()
        }
    ) {
        Icon(
            Icons.Filled.Add,
            stringResource(R.string.cont_desc_add_new_shopping_list)
        )
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
fun ShoppingListPreview() {
    val shoppingList = ProductsViewItem.ShoppingListModel("", "Weekend", 0.0, 0L, 0L)
    val product = ProductsViewItem.ProductModel("", "", "Vegetables", 19.59, 1)
    ShoppingListTheme {
        ShoppingListsScreen(
            ShoppingListViewState.ShoppingListLoaded(
                listOf(ShoppingListWithProductsModel(shoppingList, listOf(product)))
            ),
            {},
            {},
            {},
            {}
        )
    }
}
