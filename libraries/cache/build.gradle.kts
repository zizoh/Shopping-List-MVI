import Dependencies.Cache
import Dependencies.DI
import Dependencies.Test

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    namespace = "com.zizohanto.android.tobuy.cache"
    defaultConfig {
        minSdk = Config.Version.minSdkVersion
        compileSdk = Config.Version.compileSdkVersion
        targetSdk = Config.Version.targetSdkVersion

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
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }
}

dependencies {
    implementation(project(":libraries:data"))
    implementation(project(":libraries:testUtils"))

    implementation(DI.hiltAndroid)
    api(Cache.room)

    testImplementation(Test.runner)
    testImplementation(Test.androidXTest)
    testImplementation(Test.androidXTestCore)
    testImplementation(Test.robolectric)

    kapt(Cache.AnnotationProcessor.room)
    kapt(DI.AnnotationProcessor.hiltAndroid)
}
