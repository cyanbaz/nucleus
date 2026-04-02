plugins {
    id("nucleus-spring-boot")
    id("nucleus-spring-test")
    id("nucleus-jacoco")
}

dependencies {
    implementation(project(":modules:adapter-in-web"))
    implementation(project(":modules:adapter-out-memory"))
    implementation(project(":modules:application"))
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        named<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(libs.spring.boot.starter.test)
            }
        }
    }
}
