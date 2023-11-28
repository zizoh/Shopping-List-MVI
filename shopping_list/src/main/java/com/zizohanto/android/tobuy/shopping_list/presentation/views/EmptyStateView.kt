package com.zizohanto.android.tobuy.shopping_list.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zizohanto.android.tobuy.shopping_list.R
import com.zizohanto.android.tobuy.shopping_list.ui.theme.ShoppingListTheme

@Composable
fun EmptyStateView(
    title: String,
    caption: String,
    imageResId: Int,
    shouldShowButton: Boolean,
    modifier: Modifier = Modifier,
    retryClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageResId != -1) {
            Image(
                painter = painterResource(imageResId),
                contentDescription = stringResource(id = R.string.cont_desc_empty_state_icon),
                modifier = Modifier
                    .size(width = 100.dp, height = 100.dp)
            )
        }
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        Text(
            text = caption,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        if (shouldShowButton) {
            Button(
                onClick = retryClick,
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Preview
@Composable
fun EmptyStateViewPreview() {
    ShoppingListTheme {
        EmptyStateView(
            "An error occurred",
            "You don’t have any data right now",
            imageResId = R.drawable.error,
            shouldShowButton = true,
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {}
    }
}