plugins {
    id("nucleus-spring-boot")
    id("nucleus-spring-test")
}

dependencies {
    implementation(project(":modules:adapter-in-web"))
    implementation(project(":modules:adapter-out-memory"))
    implementation(project(":modules:application"))
    implementation(project(":modules:domain"))
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)
}
