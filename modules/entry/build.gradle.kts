plugins {
    id("nucleus-spring")
    id("nucleus-spring-test")
    id("nucleus-jacoco")
}

dependencies {
    implementation(libs.spring.boot.starter.webmvc)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        named<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(project(":modules:test-support"))
                implementation(libs.spring.boot.starter.webmvc.test)
                implementation(libs.jackson.module.kotlin)
            }
        }
    }
}
