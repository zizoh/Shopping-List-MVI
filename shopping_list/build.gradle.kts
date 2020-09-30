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
    kotlin(kotlinAndroidExtension)
    kotlin(kotlinKapt)
    safeArgs
    daggerHilt
}

android {
    defaultConfig {
        compileSdkVersion(Config.Version.compileSdkVersion)
        minSdkVersion(Config.Version.minSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
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
        implementation(recyclerView)
        implementation(shimmerLayout)
    }

    implementation(FlowBinding.android)
    implementation(DI.hiltAndroid)
    implementation(DI.hiltViewModel)
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
