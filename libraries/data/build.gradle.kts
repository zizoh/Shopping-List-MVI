plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

dependencies {
    implementation(project(":libraries:domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    implementation(libs.joda.time)

    testImplementation(project(":libraries:testUtils"))
}
