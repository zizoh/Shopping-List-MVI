@file:Suppress("OPT_IN_USAGE")
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrainsCompose.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.sqldelight.get().pluginId)
    id(libs.plugins.kmmbridge.get().pluginId)
    id(libs.plugins.maven.publish.get().pluginId)
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
            baseName = "ShoppingListKit"
            binaryOption("bundleId", "com.zizohanto.android.tobuy.shared")
            isStatic = true
            export("com.arkivanov.decompose:decompose:${libs.versions.decompose}")
            export("com.arkivanov.essenty:lifecycle:${libs.versions.essenty.lifecycle}")
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
            api(libs.essenty.lifecycle)

            val composeBom = project.dependencies.platform(libs.androidx.compose.bom)

            implementation(composeBom)

            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.atomicfu)
            implementation(libs.decompose.decompose)
            implementation(libs.decompose.decompose.ios)
            implementation(libs.decompose.extensions.compose.jetbrains)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqldelight.runtime)
        }
    }

    //todo consider removing if redundant
    applyDefaultHierarchyTemplate {
        common {
            group("mobile") {
                withIos()
                withAndroidTarget()
            }
        }
    }
}

android {
    namespace = "com.zizohanto.android.tobuy"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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

addGithubPackagesRepository()

kmmbridge {
    frameworkName.set("ShoppingListKit")
    mavenPublishArtifacts()
    spm()
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/zizoh/Shopping-List-MVI")
            credentials {
                username = project.findProperty("GITHUB_USERNAME") as String?
                password = project.findProperty("GITHUB_PASSWORD") as String?
            }
        }
    }
}