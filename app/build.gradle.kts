plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.jetbrainsCompose.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_17.ordinal)
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

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":shared"))

    val composeBom = platform(libs.androidx.compose.bom)

    implementation(composeBom)
    implementation(libs.androidx.activity)
    implementation(libs.decompose.decompose)
    implementation(libs.koin.android)
}