import Dependencies.Others
import Dependencies.Test

plugins {
    kotlinLibrary
}

dependencies {
    implementation(Others.jodaTime)
    testImplementation(Test.junit)
    testImplementation(Test.truth)
    testImplementation(Test.coroutinesTest)
}
