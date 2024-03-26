// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/zizoh/Shopping-List-MVI/com/zizohanto/android/tobuy/shared-kmmbridge/0.2/shared-kmmbridge-0.2.zip"
let remoteKotlinChecksum = "48ff3b1598ee5f49d3378e62d8687b0721bd981b7fdbd8d8e837e9a9b40b1948"
let packageName = "ShoppingListKit"
// END KMMBRIDGE BLOCK

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)