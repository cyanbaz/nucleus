plugins {
    id("nucleus-spring-boot")
    id("nucleus-spring-test")
}

dependencies {
    implementation(project(":modules:entry"))
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        named<JvmTestSuite>(TestSuites.INTEGRATION_TEST) {
            dependencies {
                implementation(libs.spring.boot.starter.test)
            }
        }
    }
}
