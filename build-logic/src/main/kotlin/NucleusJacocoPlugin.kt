import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

@Suppress("unused")
abstract class NucleusJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("jacoco")
            val sourceSets = extensions.getByType<SourceSetContainer>()

            val testTask = tasks.named("test")

            val integrationTestTask = tasks.findByName("integrationTest")

            tasks.named<JacocoReport>("jacocoTestReport") {
                dependsOn(testTask)

                integrationTestTask?.let {
                    dependsOn(it)
                }

                executionData.setFrom(
                    fileTree(layout.buildDirectory.dir("jacoco")) {
                        include("*.exec")
                    },
                )

                classDirectories.setFrom(
                    files(
                        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
                            exclude("**/Nucleus*.class")
                            exclude("**/generated/**")
                        },
                        fileTree(layout.buildDirectory.dir("classes/java/main")) {
                            exclude("**/generated/**")
                        },
                    ),
                )

                sourceDirectories.setFrom(sourceSets["main"].allSource.srcDirs)

                reports {
                    xml.required.set(true)
                    csv.required.set(false)
                    html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
                }
            }

            tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
                dependsOn(testTask)

                integrationTestTask?.let {
                    dependsOn(it)
                }

                finalizedBy(tasks.named("jacocoTestReport"))

                executionData.setFrom(
                    fileTree(layout.buildDirectory.dir("jacoco")) {
                        include("*.exec")
                    },
                )

                classDirectories.setFrom(
                    files(
                        fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
                            exclude("**/Nucleus*.class")
                            exclude("**/generated/**")
                        },
                        fileTree(layout.buildDirectory.dir("classes/java/main")) {
                            exclude("**/generated/**")
                        },
                    ),
                )

                violationRules {
                    rule {
                        limit {
                            minimum = "0.5".toBigDecimal()
                        }
                    }
                }
            }

            tasks.named("check") {
                dependsOn(tasks.named("jacocoTestCoverageVerification"))
            }
        }
}
