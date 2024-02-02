plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.sqldelight.get().pluginId)
}

android {
    namespace = "com.zizohanto.android.tobuy"
    defaultConfig {
        applicationId = "com.zizohanto.android.tobuy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()
        multiDexEnabled = true
        testInstrumentationRunner = "com.zizohanto.android.tobuy.utilities.CustomTestRunner"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val composeBom = platform(libs.androidx.compose.bom)

    implementation(composeBom)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.android)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.material.component)
    implementation(libs.sqldelight)

    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.androidx.test.runner)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.test.ext)

    kaptAndroidTest(libs.hilt.android.compiler)
    kaptAndroidTest(libs.androidx.hilt.compiler)
}
