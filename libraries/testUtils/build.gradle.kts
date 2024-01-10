import Dependencies.Test

plugins {
    kotlinLibrary
}

dependencies {
    implementation(project(":libraries:domain"))
    api(Test.junit)
    api(Test.truth)
    api(Test.coroutinesTest)
}
