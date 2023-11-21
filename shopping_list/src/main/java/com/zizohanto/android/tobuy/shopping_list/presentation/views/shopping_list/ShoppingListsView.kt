@file:OptIn(ExperimentalFoundationApi::class)

package com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import com.zizohanto.android.tobuy.shopping_list.presentation.views.EmptyStateView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListsView(
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
                    containerColor = colorResource(R.color.amber_primary),
                    titleContentColor = Color.Black
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
                            color = colorResource(R.color.amber_light)
                        )
                    }
                    is ShoppingListViewState.ShoppingListLoaded -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(
                                start = 8.dp,
                                top = 16.dp,
                                end = 8.dp
                            )
                        ) {
                            items(state.listWithProducts) { item ->
                                ShoppingListItem(
                                    item,
                                    listCLick,
                                    listDelete,
                                    Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                        FloatingActionButton(
                            containerColor = colorResource(R.color.amber_primary),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .navigationBarsPadding()
                                .padding(16.dp),
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
                    ShoppingListViewState.ShoppingListEmpty -> {
                        EmptyStateView(
                            stringResource(R.string.no_data),
                            "",
                            R.drawable.empty_basket,
                            shouldShowButton = false,
                            modifier = Modifier.fillMaxSize()
                        ) {}
                        FloatingActionButton(
                            containerColor = colorResource(R.color.amber_primary),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .navigationBarsPadding()
                                .padding(16.dp),
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
fun ShoppingListTitle(shoppingListTitle: String) {
    Text(
        text = shoppingListTitle,
        color = Color.Black,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
    )
}

@Composable
fun ProductItem(
    productName: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (checkbox, productText) = createRefs()

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(checkbox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_check_box_outline_blank_24),
                contentDescription = stringResource(id = R.string.cont_desc_select_button),
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = productName,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .constrainAs(productText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(checkbox.end)
                }
                .wrapContentWidth(Alignment.Start)

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
            .border(1.dp, colorResource(R.color.amber_light), shape = RoundedCornerShape(8.dp))
    ) {
        ShoppingListTitle(shoppingList.name)
        // Todo: use lazy column
        Column {
            listWithProducts.products.forEach {
                ProductItem(it.name)
            }
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

@Preview
@Composable
fun ShoppingListPreview() {
    val shoppingList = ProductsViewItem.ShoppingListModel("", "Weekend", 0.0, 0L, 0L)
    val product = ProductsViewItem.ProductModel("", "", "Vegetables", 19.59, 1)
    ShoppingListItem(
        ShoppingListWithProductsModel(shoppingList, listOf(product)),
        {},
        {}
    )
}
