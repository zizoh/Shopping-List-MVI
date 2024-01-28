plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.sqldelight.get().pluginId)
}

android {
    namespace = "com.zizohanto.android.tobuy.core"
    defaultConfig {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

sqldelight {
    databases {
        create("ShoppingListDatabase") {
            packageName.set("com.zizohanto.android.tobuy.core.sq")
        }
    }
}

dependencies {

    implementation(libs.hilt.android)
    implementation(libs.javax.inject)
    implementation(libs.joda.time)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.sqldelight)

    testImplementation(libs.androidx.test.ext)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.google.truth)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    kapt(libs.hilt.android.compiler)
}
