package com.zizohanto.android.tobuy.presentation.views

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyStateView(
    title: String,
    caption: String,
    imageResId: String?,
    shouldShowButton: Boolean,
    modifier: Modifier = Modifier,
    retryClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!imageResId.isNullOrEmpty()) {
            Image(
                painter = painterResource(imageResId),
                contentDescription = "Empty state icon",
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
                Text(text = "Retry")
            }
        }
    }
}