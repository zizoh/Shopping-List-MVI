import Dependencies.AndroidX
import Dependencies.Coroutines
import Dependencies.DI
import Dependencies.Test
import Dependencies.View

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

    implementation(DI.hiltAndroid)
    implementation(DI.hiltNavigationCompose)
    implementation(composeBom)
    implementAll(AndroidX.components)
    implementAll(Coroutines.components)

    kapt(DI.AnnotationProcessor.hiltAndroid)
    kapt(DI.AnnotationProcessor.hiltCompiler)

    testImplementation(Test.coroutinesTest)
    testImplementation(Test.mockk)
    testImplementation(Test.mockkAgent)
    testImplementation(Test.runner)

    debugImplementation(Test.composeUITestManifest)
    androidTestImplementation(Test.composeUITestJUnit)
    androidTestImplementation(Test.hiltTesting)
    androidTestImplementation(Test.espresso)
    androidTestImplementation(Test.espressoContrib)
    androidTestImplementation(Test.fragmentTesting)
    androidTestImplementation(Test.rules)
    androidTestImplementation(Test.archCoreTest)
    androidTestImplementation(Test.androidXTest)

    kaptAndroidTest(DI.AnnotationProcessor.hiltAndroid)
    kaptAndroidTest(DI.AnnotationProcessor.hiltCompiler)
}
