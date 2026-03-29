import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
abstract class NucleusSpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("com.diffplug.spotless")

            extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
                kotlin {
                    ktlint()
                    target("**/src/**/*.kt")
                    targetExclude("**/build/**")
                }
                kotlinGradle {
                    ktlint()
                    target("*.gradle.kts", "**/*.gradle.kts")
                    targetExclude("**/build/**")
                }
                yaml {
                    target("**/*.yaml")
                    jackson()
                        .feature("INDENT_OUTPUT", true)
                        .feature("ORDER_MAP_ENTRIES_BY_KEYS", true)
                        .yamlFeature("WRITE_DOC_START_MARKER", false)
                        .yamlFeature("MINIMIZE_QUOTES", true)
                        .yamlFeature("SPLIT_LINES", false)
                }
                json {
                    target("**/*.json", "**/*.json5")
                    gson().indentWithSpaces(6).sortByKeys().escapeHtml()
                }
            }
        }
}
