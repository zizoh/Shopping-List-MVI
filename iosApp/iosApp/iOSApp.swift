import SwiftUI
import ShoppingListKit

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

	var body: some Scene {
		WindowGroup {
			ContentView(appDelegate.root)
		}
	}
}