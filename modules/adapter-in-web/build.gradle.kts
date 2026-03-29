plugins {
    id("nucleus-spring")
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.web)
}
