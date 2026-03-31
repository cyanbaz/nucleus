import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.jvm.JvmTestSuite
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.base.TestingExtension

@Suppress("unused", "UnstableApiUsage")
abstract class NucleusTestPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("jvm-test-suite")
            pluginManager.apply("nucleus-jacoco")
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<TestingExtension> {
                suites {
                    withType<JvmTestSuite> {
                        useJUnitJupiter()
                        dependencies { implementation(libs.findLibrary("assertj.core").get()) }
                    }
                }
            }
        }
}
