plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
}

android {
    namespace = "com.zizohanto.android.tobuy.shopping_list"
    defaultConfig {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        testInstrumentationRunner =
            "com.zizohanto.android.tobuy.shopping_list.utilities.CustomTestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    val coreDependencyNotation = project(":core")
    val presentationDependencyNotation = project(":presentation")
    val dependencyNotation = project(":libraries:cache")
    val domainDependencyNotation = project(":libraries:domain")
    val testUtilsDependencyNotation = project(":libraries:testUtils")

    implementation(coreDependencyNotation)
    implementation(presentationDependencyNotation)
    implementation(domainDependencyNotation)

    testImplementation(testUtilsDependencyNotation)
    testImplementation(coreDependencyNotation)
    testImplementation(presentationDependencyNotation)
    testImplementation(domainDependencyNotation)
    testImplementation(dependencyNotation)
    androidTestImplementation(coreDependencyNotation)
    androidTestImplementation(presentationDependencyNotation)
    androidTestImplementation(domainDependencyNotation)
    androidTestImplementation(dependencyNotation)
    androidTestImplementation(testUtilsDependencyNotation)

    implementation(composeBom)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.material.component)

    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.androidx.test.runner)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.test.ext)

    kaptAndroidTest(libs.hilt.android.compiler)
    kaptAndroidTest(libs.androidx.hilt.compiler)
}
