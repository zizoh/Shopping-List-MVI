import androidx.compose.ui.window.ComposeUIViewController
import com.zizohanto.android.tobuy.ui.DefaultAppComponent
import com.zizohanto.android.tobuy.ui.ShoppingListApp

fun MainViewController(component: DefaultAppComponent) = ComposeUIViewController {
    ShoppingListApp(component)
}