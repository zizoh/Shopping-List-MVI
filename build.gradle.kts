import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
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
