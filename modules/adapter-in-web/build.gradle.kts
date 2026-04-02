plugins {
    id("nucleus-spring")
    id("nucleus-spring-test")
    id("nucleus-jacoco")
}

dependencies {
    implementation(project(":modules:application"))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.web)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        named<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(project(":modules:application"))
                implementation(libs.spring.boot.starter.webmvc.test)
                implementation(libs.jackson.module.kotlin)
            }
        }
    }
}
