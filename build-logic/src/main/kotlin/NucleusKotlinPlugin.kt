import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
abstract class NucleusKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            pluginManager.apply("org.jetbrains.kotlin.plugin.spring")
            pluginManager.apply("nucleus-spotless")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<JavaPluginExtension> {
                toolchain.languageVersion.set(
                    JavaLanguageVersion.of(
                        libs
                            .findVersion("java")
                            .get()
                            .requiredVersion
                            .toInt(),
                    ),
                )
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlin.reflect").get())
            }
        }
}
