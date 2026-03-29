package de.cyanbaz.conventions

import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins { `jvm-test-suite` }

val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

@Suppress("UnstableApiUsage")
testing {
    suites {
        val test by
            getting(JvmTestSuite::class) {
                useJUnitJupiter()

                dependencies { implementation(libs.findLibrary("assertj.core").get()) }
            }

        register<JvmTestSuite>("integrationTest") {
            dependencies {
                implementation(project())
                implementation(libs.findLibrary("spring.boot.starter.webmvc.test").get())
            }

            targets {
                all {
                    testTask.configure {
                        testLogging {
                            exceptionFormat = TestExceptionFormat.FULL
                            events("skipped", "failed")
                        }
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

tasks.named("check") { dependsOn(tasks.named("jacocoTestCoverageVerification")) }
