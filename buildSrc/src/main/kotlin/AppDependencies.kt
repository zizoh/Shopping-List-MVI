import Dependencies.View.Version.fragment

const val kotlinAndroid: String = "android"
const val kotlinAndroidExtension: String = "android.extensions"
const val kotlinKapt: String = "kapt"
const val ktLintVersion: String = "0.36.0"

object Config {
    object Version {
        const val minSdkVersion: Int = 21
        const val compileSdkVersion: Int = 29
        const val targetSdkVersion: Int = 29
        const val versionName: String = "1.0"
        const val versionCode: Int = 1
    }

    const val isMultiDexEnabled: Boolean = true

    object Android {
        const val applicationId: String = "com.zizohanto.android.tobuy"
        const val testInstrumentationRunner: String = "androidx.test.runner.AndroidJUnitRunner"
    }
}

interface Libraries {
    val components: List<String>
}

object Dependencies {
    object AndroidX : Libraries {
        object Version {
            const val coreKtx: String = "1.5.0-alpha02"
            const val navigation: String = "2.3.0"
            const val multidex: String = "2.0.1"
            const val lifeCycle: String = "2.3.0-alpha07"
            const val activity: String = "1.2.0-alpha08"
        }

        const val coreKtx: String = "androidx.core:core-ktx:${Version.coreKtx}"
        const val navigationFragmentKtx: String = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        const val navigationUiKtx: String = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
        const val multiDex: String = "androidx.multidex:multidex:${Version.multidex}"
        const val activity: String = "androidx.activity:activity:${Version.activity}"
        const val lifeCycleCommon: String = "androidx.lifecycle:lifecycle-common-java8:${Version.lifeCycle}"
        const val viewModel: String = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifeCycle}"

        override val components: List<String>
            get() = listOf(
                    coreKtx, navigationFragmentKtx, navigationUiKtx, multiDex, activity,
                    lifeCycleCommon, viewModel
            )
    }

    object View : Libraries {
        object Version {
            const val materialComponent: String = "1.3.0-alpha02"
            const val shimmerLayout: String = "0.5.0"
            const val appCompat: String = "1.3.0-alpha02"
            const val constraintLayout: String = "2.0.1"
            const val fragment: String = "1.3.0-alpha08"
            const val recyclerView: String = "1.2.0-alpha05"
        }

        const val appCompat: String = "androidx.appcompat:appcompat:${Version.appCompat}"
        const val fragment: String = "androidx.fragment:fragment-ktx:${Version.fragment}"
        const val materialComponent: String = "com.google.android.material:material:${Version.materialComponent}"
        const val shimmerLayout: String = "com.facebook.shimmer:shimmer:${Version.shimmerLayout}"
        const val constraintLayout: String = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val recyclerView: String = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
        override val components: List<String> = listOf(appCompat, fragment)
    }

    object FlowBinding {
        private const val flowBinding: String = "1.0.0-alpha04"
        const val android: String = "io.github.reactivecircus.flowbinding:flowbinding-android:$flowBinding"
        const val lifecycle: String = "io.github.reactivecircus.flowbinding:flowbinding-lifecycle:$flowBinding"
    }

    object DI {
        object Version {
            const val javaxInject: String = "1"
            const val hiltAndroid: String = "2.28.3-alpha"
            const val hiltViewModel: String = "1.0.0-alpha02"
        }

        object AnnotationProcessor {
            const val hiltAndroid: String = "com.google.dagger:hilt-android-compiler:${Version.hiltAndroid}"
            const val hiltCompiler: String = "androidx.hilt:hilt-compiler:${Version.hiltViewModel}"
        }

        const val javaxInject: String = "javax.inject:javax.inject:${Version.javaxInject}"
        const val hiltAndroid: String = "com.google.dagger:hilt-android:${Version.hiltAndroid}"
        const val hiltViewModel: String = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hiltViewModel}"
        const val hiltTesting: String = "com.google.dagger:hilt-android-testing:${Version.hiltAndroid}"
    }

    object Coroutines : Libraries {
        object Version {
            const val coroutines: String = "1.3.9"
        }

        const val core: String = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
        const val android: String = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

        override val components: List<String> = listOf(core, android)
    }

    object Cache {
        object Version {
            const val room: String = "2.3.0-alpha02"
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
            const val espresso: String = "3.3.0"
            const val truth: String = "1.0.1"
            const val robolectric: String = "4.4"
            const val archCoreTest: String = "1.1.1"
        }

        const val junit: String = "junit:junit:${Version.junit}"
        const val runner: String = "androidx.test:runner:${Version.runner}"
        const val rules: String = "androidx.test:rules:${Version.rules}"
        const val fragmentTesting: String = "androidx.fragment:fragment-testing:$fragment"
        const val androidXTest: String = "androidx.test.ext:junit:${Version.testExt}"
        const val espresso: String = "androidx.test.espresso:espresso-core:${Version.espresso}"
        const val espressoContrib: String = "androidx.test.espresso:espresso-contrib:${Version.espresso}"
        const val archCoreTest: String = "android.arch.core:core-testing:${Version.archCoreTest}"
        const val truth: String = "com.google.truth:truth:${Version.truth}"
        const val coroutinesTest: String = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Coroutines.Version.coroutines}"
        const val robolectric: String = "org.robolectric:robolectric:${Version.robolectric}"
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
}
