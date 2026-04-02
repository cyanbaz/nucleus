plugins {
    id("nucleus-kotlin")
    id("nucleus-test")
    id("nucleus-jacoco")
}

dependencies {
    implementation(project(":modules:application"))
    implementation(project(":modules:domain"))
}
