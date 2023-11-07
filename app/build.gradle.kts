import Dependencies.AndroidX
import Dependencies.DI
import Dependencies.Others
import Dependencies.View
import ProjectLib.cache
import ProjectLib.core
import ProjectLib.data
import ProjectLib.domain
import ProjectLib.presentation
import ProjectLib.shoppingList

plugins {
    androidApplication
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    safeArgs
    daggerHilt
}

android {
    namespace = Config.Android.applicationId
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdk = Config.Version.minSdkVersion
        compileSdk = Config.Version.compileSdkVersion
        versionCode = Config.Version.versionCode
        versionName = Config.Version.versionName
        multiDexEnabled = Config.isMultiDexEnabled
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
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
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
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

    implementation(project(shoppingList))
    implementation(project(cache))
    implementation(project(presentation))
    implementation(project(domain))
    implementation(project(data))
    implementation(project(core))

    implementAll(View.components)
    implementation(DI.hiltAndroid)
    implementation(Others.jodaTime)

    AndroidX.run {
        implementation(activity)
        implementation(coreKtx)
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)
        implementation(multiDex)
    }

    kapt(DI.AnnotationProcessor.hiltAndroid)
    kapt(DI.AnnotationProcessor.hiltCompiler)
}
