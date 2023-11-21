@file:OptIn(ExperimentalFoundationApi::class)

package com.zizohanto.android.tobuy.shopping_list.presentation.views.shopping_list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import androidx.core.view.isVisible
import com.zizohanto.android.tobuy.core.ext.getImage
import com.zizohanto.android.tobuy.presentation.mvi.MVIView
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.databinding.LayoutShoppingListBinding
import com.zizohanto.android.tobuy.shopping_list.navigation.NavigationDispatcher
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ProductsViewItem
import com.zizohanto.android.tobuy.shopping_list.presentation.models.ShoppingListWithProductsModel
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewIntent
import com.zizohanto.android.tobuy.shopping_list.presentation.shopping_list.mvi.ShoppingListViewState
import com.zizohanto.android.tobuy.shopping_list.ui.shopping_list.adaper.ShoppingListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListsView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet),
    MVIView<ShoppingListViewIntent, ShoppingListViewState> {

    @Inject
    lateinit var shoppingListAdapter: ShoppingListAdapter

    @Inject
    lateinit var navigator: NavigationDispatcher

    private var binding: LayoutShoppingListBinding

    private var clickListener: (String) -> Unit = {}

    private var deleteListener: (String) -> Unit = {}

    private var retryListener: () -> Unit = {}

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = LayoutShoppingListBinding.inflate(inflater, this, true)
        with(binding) {
            shoppingLists.adapter = shoppingListAdapter
        }
    }

    fun render(
        state: ShoppingListViewState,
        listCLick: (String) -> Unit,
        listDelete: (String) -> Unit,
        create: () -> Unit,
        retry: () -> Unit
    ) {
        clickListener = listCLick
        deleteListener = listDelete
        retryListener = retry
        render(state)
        binding.addShoppingList.setOnClickListener {
            create.invoke()
        }
    }

    override fun render(state: ShoppingListViewState) {
        when (state) {
            ShoppingListViewState.Idle -> {
            }
            ShoppingListViewState.Loading -> {
                with(binding) {
                    progressBar.isVisible = !shoppingLists.isVisible
                    emptyState.isVisible = false
                }
            }
            is ShoppingListViewState.ShoppingListLoaded -> {
                with(binding) {
                    progressBar.isVisible = false
                    emptyState.isVisible = false
                    shoppingLists.isVisible = true
                }
                shoppingListAdapter.submitList(
                    state.listWithProducts,
                    clickListener,
                    deleteListener
                )
            }
            ShoppingListViewState.ShoppingListEmpty -> {
                shoppingListAdapter.reset()
                with(binding) {
                    progressBar.isVisible = false
                    shoppingLists.isVisible = false
                    emptyState.isVisible = true
                    emptyState.setImage(context.getImage(R.drawable.empty_basket))
                    emptyState.setTitle(context.getString(R.string.no_data))
                    emptyState.resetCaption()
                    emptyState.isButtonVisible = false
                }
            }
            is ShoppingListViewState.Error -> {
                with(binding) {
                    shoppingLists.isVisible = false
                    progressBar.isVisible = false
                    emptyState.isVisible = true
                    emptyState.setImage(context.getImage(R.drawable.error))
                    emptyState.setCaption(state.message)
                    emptyState.setTitle(context.getString(R.string.an_error_occurred))
                    emptyState.isButtonVisible = true
                    binding.emptyState.setOnClickListener {
                        retryListener.invoke()
                    }
                }
            }
            is ShoppingListViewState.NewShoppingListLoaded -> {
                state.openProductScreen.consume(navigator::openShoppingListDetail)
            }
        }
    }

    override val intents: Flow<ShoppingListViewIntent> = flowOf()
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
    onDelete: (String) -> Unit
) {
    val shoppingList = listWithProducts.shoppingList
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    onClick.invoke(shoppingList.id)
                },
                onLongClick = {
                    showDialog = true
                }
            )
            .padding(8.dp)
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
