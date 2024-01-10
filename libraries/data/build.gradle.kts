import Dependencies.Others

plugins {
    kotlinLibrary
}

dependencies {
    implementation(project(":libraries:domain"))
    implementation(Others.jodaTime)

    testImplementation(project(":libraries:testUtils"))
}
