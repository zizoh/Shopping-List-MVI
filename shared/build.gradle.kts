import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrainsCompose.get().pluginId)
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.sqldelight.get().pluginId)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            export("com.arkivanov.decompose:decompose:${libs.versions.decompose}")
            export("com.arkivanov.essenty:lifecycle:1.3.0")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.decompose.extensions.compose.jetpack)
            implementation(libs.material.component)
            implementation(libs.sqldelight.android.driver)
        }

        appleMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }

        commonMain.dependencies {
            val composeBom = project.dependencies.platform(libs.androidx.compose.bom)

            implementation(composeBom)

            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.decompose.decompose)
            implementation(libs.decompose.decompose.ios)
            implementation(libs.decompose.extensions.compose.jetbrains)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqldelight.runtime)
        }
    }
}

android {
    namespace = "com.zizohanto.android.tobuy"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        multiDexEnabled = true
        testInstrumentationRunner = "com.zizohanto.android.tobuy.utilities.CustomTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

sqldelight {
    databases {
        create("ShoppingListDatabase") {
            packageName.set("com.zizohanto.android.tobuy.sq")
        }
    }
}