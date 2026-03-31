plugins {
    id("nucleus-kotlin")
    id("nucleus-test")
    id("nucleus-jacoco")
}

dependencies {
    implementation(project(":modules:domain"))
}
