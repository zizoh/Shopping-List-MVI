import Dependencies.Coroutines
import Dependencies.DI

plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Coroutines.core)
    implementation(DI.javaxInject)
}
