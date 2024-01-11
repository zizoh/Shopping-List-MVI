plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

dependencies {
    implementation(project(":libraries:domain"))
    api(libs.junit)
    api(libs.google.truth)
    api(libs.kotlinx.coroutines.test)
}
