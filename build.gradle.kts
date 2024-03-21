import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kmmbridge) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.spotless.gradle.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

subprojects {
//    applySpotless
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs +=
            "-Xuse-experimental=" +
                    "kotlin.Experimental," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.InternalCoroutinesApi," +
                    "kotlinx.coroutines.ObsoleteCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview"
    }
}
