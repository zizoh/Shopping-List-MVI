import SwiftUI
import ShoppingListKit

class AppDelegate: NSObject, UIApplicationDelegate {
    let root: AppComponent = DefaultAppComponent(
        componentContext = DefaultComponentContext(lifecycle: ApplicationLifecycle())
    )
}