import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.FlowBinding
import Dependencies.Test
import Dependencies.View
import ProjectLib.core
import ProjectLib.domain
import ProjectLib.presentation
import ProjectLib.testUtils

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    safeArgs
    daggerHilt
}

android {
    namespace = "com.zizohanto.android.tobuy.shopping_list"
    defaultConfig {
        compileSdk = Config.Version.compileSdkVersion
        minSdk = Config.Version.minSdkVersion
        targetSdk = Config.Version.targetSdkVersion
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = View.Version.kotlinCompilerExtensionVersion
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    val composeBom = platform(Dependencies.View.composeBom)
    implementation(project(core))
    implementation(project(presentation))
    implementation(project(domain))

    testImplementation(project(testUtils))
    androidTestImplementation(project(testUtils))

    with(View) {
        implementAll(components)
        implementation(fragment)
        implementation(materialComponent)
        implementation(constraintLayout)
        implementation(constraintLayoutCompose)
        implementation(recyclerView)
        implementation(shimmerLayout)
        implementation(composeRuntime)
        implementation(composeUi)
        implementation(composeUiTooling)
        implementation(composeUiToolingPreview)
        implementation(composeFoundation)
        implementation(composeFoundationLayout)
        implementation(composeMaterial)
    }

    implementation(FlowBinding.android)
    implementation(DI.hiltAndroid)
    implementation(DI.hiltNavigationCompose)
    implementation(composeBom)
    implementAll(AndroidX.components)
    implementAll(Coroutines.components)

    kapt(DI.AnnotationProcessor.hiltAndroid)
    kapt(DI.AnnotationProcessor.hiltCompiler)

    androidTestImplementation(DI.hiltTesting)
    androidTestImplementation(Test.espresso)
    androidTestImplementation(Test.espressoContrib)
    androidTestImplementation(Test.fragmentTesting)
    androidTestImplementation(Test.rules)
    androidTestImplementation(Test.archCoreTest)

    androidTestImplementation(Test.runner)
    androidTestImplementation(Test.androidXTest)

    kaptAndroidTest(DI.AnnotationProcessor.hiltAndroid)
    kaptAndroidTest(DI.AnnotationProcessor.hiltCompiler)
}
