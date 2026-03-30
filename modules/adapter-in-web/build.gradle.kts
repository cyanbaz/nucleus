plugins {
    id("nucleus-spring")
    id("nucleus-spring-test")
}

dependencies {
    implementation(project(":modules:application"))
    implementation(project(":modules:domain"))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.web)
}
