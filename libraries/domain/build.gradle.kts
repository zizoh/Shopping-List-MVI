plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.joda.time)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    testImplementation(libs.kotlinx.coroutines.test)
}
