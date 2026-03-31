import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

@Suppress("unused")
abstract class NucleusJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("jacoco")

            val sourceSets = extensions.getByType<SourceSetContainer>()
            val mainSourceSet = sourceSets.named("main")

            val testTasks =
                tasks.withType<Test>().matching {
                    it.name == TestSuites.TEST || it.name == TestSuites.INTEGRATION_TEST
                }

            val coverageExcludes =
                listOf(
                    "**/*Application.class",
                    "**/generated/**",
                )

            tasks.named<JacocoReport>("jacocoTestReport") {
                dependsOn(testTasks)

                executionData.from(
                    testTasks.mapNotNull { testTask ->
                        testTask.extensions.findByType(JacocoTaskExtension::class.java)?.destinationFile
                    },
                )

                val main = mainSourceSet.get()
                sourceDirectories.from(main.allSource.srcDirs)
                classDirectories.from(
                    main.output.classesDirs.asFileTree.matching {
                        coverageExcludes.forEach(::exclude)
                    },
                )

                reports {
                    xml.required.set(true)
                    html.required.set(true)
                    csv.required.set(false)
                }
            }

            tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
                dependsOn(testTasks)

                executionData.from(
                    testTasks.mapNotNull { testTask ->
                        testTask.extensions.findByType(JacocoTaskExtension::class.java)?.destinationFile
                    },
                )

                val main = mainSourceSet.get()
                classDirectories.from(
                    main.output.classesDirs.asFileTree.matching {
                        coverageExcludes.forEach(::exclude)
                    },
                )

                violationRules {
                    rule {
                        limit {
                            minimum = "0.5".toBigDecimal()
                        }
                    }
                }
            }

            tasks.named("jacocoTestCoverageVerification") {
                finalizedBy("jacocoTestReport")
            }

            tasks.named("check") {
                dependsOn("jacocoTestCoverageVerification")
            }
        }
}
