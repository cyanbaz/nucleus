plugins {
    id("nucleus-spring")
    id("nucleus-spring-test")
    id("nucleus-jacoco")
}

dependencies {
    implementation(libs.spring.web)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        named<JvmTestSuite>(TestSuites.TEST) {
            dependencies {
                implementation(libs.archunit)
            }
        }
        named<JvmTestSuite>(TestSuites.INTEGRATION_TEST) {
            dependencies {
                implementation(project(":modules:test-support"))
                implementation(libs.spring.boot.starter.webmvc.test)
                implementation(libs.jackson.module.kotlin)
            }
        }
    }
}
