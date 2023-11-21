package com.zizohanto.android.tobuy.shopping_list.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zizohanto.android.tobuy.shopping_list.R

@Composable
fun EmptyStateView(
    title: String,
    caption: String,
    imageResId: Int,
    shouldShowButton: Boolean,
    retryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        Text(
            text = caption,
            color = Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        if (shouldShowButton) {
            Button(onClick = retryClick) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Preview
@Composable
fun EmptyStateViewPreview() {
    EmptyStateView(
        "An error occured",
        "You don’t have any data right now",
        imageResId = R.drawable.error,
        shouldShowButton = true
    ) {}
}