import UIKit
import SwiftUI
import ShoppingListKit

struct ComposeView: UIViewControllerRepresentable {
    private let root: AppComponent

    init(_ root: AppComponent) {
        self.root = root
    }

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(root)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    private let root: AppComponent

    init(_ root: AppComponent) {
        self.root = root
    }

    var body: some View {
        ComposeView(root)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}