package com.zizohanto.android.tobuy.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.zizohanto.android.tobuy.ui.theme.ShoppingListTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                ShoppingListApp()
            }
        }
    }
}