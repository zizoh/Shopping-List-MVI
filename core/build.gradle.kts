import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.View
import ProjectLib.data
import ProjectLib.domain

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    namespace = "com.zizohanto.android.tobuy.core"
    compileSdk = Config.Version.compileSdkVersion
    defaultConfig {
        minSdk = Config.Version.minSdkVersion
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
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }
}

dependencies {
    implementation(project(domain))
    implementation(project(data))

    implementation(AndroidX.lifeCycleCommon)
    implementation(View.appCompat)
    implementation(View.fragment)
    implementation(DI.hiltAndroid)
    implementation(Coroutines.core)

    kapt(DI.AnnotationProcessor.hiltAndroid)
}
