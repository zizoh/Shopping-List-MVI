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
    compileSdkVersion(Config.Version.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Config.Version.minSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
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
