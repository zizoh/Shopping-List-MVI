import Dependencies.View.Version.fragment

const val kotlinAndroid: String = "android"
const val kotlinKapt: String = "kapt"
const val ktLintVersion: String = "0.36.0"

object Config {
    object Version {
        const val minSdkVersion: Int = 21
        const val compileSdkVersion: Int = 34
        const val targetSdkVersion: Int = 34
        const val versionName: String = "1.0"
        const val versionCode: Int = 1
    }

    const val isMultiDexEnabled: Boolean = true

    object Android {
        const val applicationId: String = "com.zizohanto.android.tobuy"
        const val testInstrumentationRunner: String = "com.zizohanto.android.tobuy.shopping_list.utilities.CustomTestRunner"
    }
}

interface Libraries {
    val components: List<String>
}

object Dependencies {
    object AndroidX : Libraries {
        object Version {
            const val coreKtx: String = "1.12.0"
            const val navigation: String = "2.7.5"
            const val multidex: String = "2.0.1"
            const val lifeCycle: String = "2.6.2"
            const val activity: String = "1.8.0"
        }

        const val coreKtx: String = "androidx.core:core-ktx:${Version.coreKtx}"
        const val navigationFragmentKtx: String =
            "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        const val navigationUiKtx: String =
            "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
        const val navigationCompose: String = "androidx.navigation:navigation-compose"
        const val multiDex: String = "androidx.multidex:multidex:${Version.multidex}"
        const val activity: String = "androidx.activity:activity:${Version.activity}"
        const val lifeCycleCommon: String =
            "androidx.lifecycle:lifecycle-common-java8:${Version.lifeCycle}"
        const val viewModel: String =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifeCycle}"
        const val viewModelCompose: String =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifeCycle}"

        override val components: List<String>
            get() = listOf(
                coreKtx, navigationFragmentKtx, navigationUiKtx, navigationCompose, multiDex, activity,
                lifeCycleCommon, viewModel, viewModelCompose
            )
    }

    object View : Libraries {
        object Version {
            const val materialComponent: String = "1.3.0-alpha02"
            const val shimmerLayout: String = "0.5.0"
            const val appCompat: String = "1.3.0-alpha02"
            const val constraintLayout: String = "2.0.1"
            const val constraintLayoutCompose: String = "1.0.1"
            const val fragment: String = "1.3.0-alpha08"
            const val recyclerView: String = "1.2.0-alpha05"
            const val kotlinCompilerExtensionVersion = "1.5.4"
            const val composeBom = "2023.10.01"
        }

        const val appCompat: String = "androidx.appcompat:appcompat:${Version.appCompat}"
        const val fragment: String = "androidx.fragment:fragment-ktx:${Version.fragment}"
        const val materialComponent: String =
            "com.google.android.material:material:${Version.materialComponent}"
        const val shimmerLayout: String = "com.facebook.shimmer:shimmer:${Version.shimmerLayout}"
        const val constraintLayout: String =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val constraintLayoutCompose: String =
            "androidx.constraintlayout:constraintlayout-compose:${Version.constraintLayoutCompose}"
        const val recyclerView: String =
            "androidx.recyclerview:recyclerview:${Version.recyclerView}"
        const val composeBom: String = "androidx.compose:compose-bom:${Version.composeBom}"
        const val composeRuntime = "androidx.compose.runtime:runtime"
        const val composeUi = "androidx.compose.ui:ui"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeFoundation = "androidx.compose.foundation:foundation"
        const val composeFoundationLayout = "androidx.compose.foundation:foundation-layout"
        const val composeMaterial = "androidx.compose.material3:material3"
        override val components: List<String> = listOf(appCompat, fragment)
    }

    object Others {
        object Version {
            const val jodaTime: String = "2.9.9.4"
        }

        const val jodaTime: String = "net.danlew:android.joda:${Version.jodaTime}"
    }

    object FlowBinding {
        private const val flowBinding: String = "1.0.0-alpha04"
        const val android: String =
            "io.github.reactivecircus.flowbinding:flowbinding-android:$flowBinding"
        const val lifecycle: String =
            "io.github.reactivecircus.flowbinding:flowbinding-lifecycle:$flowBinding"
    }

    object DI {
        object Version {
            const val javaxInject: String = "1"
            const val hiltAndroid: String = "2.48.1"
            const val hiltNavigationCompose: String = "1.1.0"
            const val hiltViewModel: String = "1.0.0-alpha02"
        }

        object AnnotationProcessor {
            const val hiltAndroid: String =
                "com.google.dagger:hilt-android-compiler:${Version.hiltAndroid}"
            const val hiltCompiler: String = "androidx.hilt:hilt-compiler:${Version.hiltViewModel}"
        }

        const val javaxInject: String = "javax.inject:javax.inject:${Version.javaxInject}"
        const val hiltAndroid: String = "com.google.dagger:hilt-android:${Version.hiltAndroid}"
        const val hiltNavigationCompose: String = "androidx.hilt:hilt-navigation-compose:${Version.hiltNavigationCompose}"
    }

    object Coroutines : Libraries {
        object Version {
            const val coroutines: String = "1.3.9"
        }

        const val core: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
        const val android: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

        override val components: List<String> = listOf(core, android)
    }

    object Cache {
        object Version {
            const val room: String = "2.6.0"
        }

        object AnnotationProcessor {
            const val room: String = "androidx.room:room-compiler:${Version.room}"
        }

        const val room: String = "androidx.room:room-ktx:${Version.room}"
    }

    object Test {
        object Version {
            const val junit: String = "4.13"
            const val runner: String = "1.3.0"
            const val rules: String = "1.3.0"
            const val testExt: String = "1.1.2"
            const val testCore: String = "1.3.0"
            const val espresso: String = "3.3.0"
            const val truth: String = "1.0.1"
            const val robolectric: String = "4.4"
            const val archCoreTest: String = "1.1.1"
            const val mockk: String = "1.13.8"
        }

        const val junit: String = "junit:junit:${Version.junit}"
        const val runner: String = "androidx.test:runner:${Version.runner}"
        const val mockk = "io.mockk:mockk-android:${Version.mockk}"
        const val mockkAgent = "io.mockk:mockk-agent:${Version.mockk}"
        const val rules: String = "androidx.test:rules:${Version.rules}"
        const val fragmentTesting: String = "androidx.fragment:fragment-testing:$fragment"
        const val androidXTest: String = "androidx.test.ext:junit:${Version.testExt}"
        const val androidXTestCore: String = "androidx.test:core:${Version.testCore}"
        const val espresso: String = "androidx.test.espresso:espresso-core:${Version.espresso}"
        const val espressoContrib: String =
            "androidx.test.espresso:espresso-contrib:${Version.espresso}"
        const val archCoreTest: String = "android.arch.core:core-testing:${Version.archCoreTest}"
        const val truth: String = "com.google.truth:truth:${Version.truth}"
        const val coroutinesTest: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Coroutines.Version.coroutines}"
        const val robolectric: String = "org.robolectric:robolectric:${Version.robolectric}"
        const val composeUITestManifest = "androidx.compose.ui:ui-test-manifest"
        const val composeUITestJUnit = "androidx.compose.ui:ui-test-junit4"
        const val hiltTesting: String =
            "com.google.dagger:hilt-android-testing:${DI.Version.hiltAndroid}"
    }
}

object ProjectLib {
    const val app: String = ":app"
    const val core: String = ":core"
    const val presentation: String = ":presentation"
    const val domain: String = ":libraries:domain"
    const val data: String = ":libraries:data"
    const val cache: String = ":libraries:cache"
    const val testUtils: String = ":libraries:testUtils"
    const val shoppingList: String = ":shopping_list"
}
