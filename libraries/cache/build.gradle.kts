import Dependencies.Cache
import Dependencies.DI
import Dependencies.Test
import ProjectLib.data
import ProjectLib.testUtils

plugins {
    androidLibrary
    kotlin(kotlinAndroid)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    defaultConfig {
        minSdkVersion(Config.Version.minSdkVersion)
        compileSdkVersion(Config.Version.compileSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(Pair("room.incremental", "true"))
            }
        }
        buildConfigField("int", "databaseVersion", 1.toString())
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
    }
}

dependencies {
    implementation(project(data))
    implementation(project(testUtils))

    implementation(DI.hiltAndroid)
    api(Cache.room)

    testImplementation(Test.runner)
    testImplementation(Test.androidXTest)
    testImplementation(Test.androidXTestCore)
    testImplementation(Test.robolectric)

    kapt(Cache.AnnotationProcessor.room)
    kapt(DI.AnnotationProcessor.hiltAndroid)
}
