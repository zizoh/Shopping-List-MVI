plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
}

android {
    namespace = "com.zizohanto.android.tobuy.cache"
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(Pair("room.incremental", "true"))
            }
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":libraries:data"))
    implementation(project(":libraries:testUtils"))

    implementation(libs.hilt.android)
    api(libs.android.room.ktx)

    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.androidx.test.ext)
    testImplementation(libs.androidx.test.core)

    kapt(libs.androidx.room.compiler)
    kapt(libs.hilt.android.compiler)
}
