// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/zizoh/Shopping-List-MVI/ShoppingList/shared-kmmbridge/unspecified/shared-kmmbridge-unspecified.zip"
let remoteKotlinChecksum = "0672bda61a948c7507a950facab792b4475ca56762dadd3c231225294d7c0718"
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