plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
}

android {
    namespace = "com.zizohanto.android.tobuy.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
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

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":libraries:data"))
    implementation(project(":libraries:domain"))

    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.core)

    kapt(libs.hilt.android.compiler)
}
