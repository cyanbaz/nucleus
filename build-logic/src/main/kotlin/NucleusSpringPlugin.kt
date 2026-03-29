import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
abstract class NucleusSpringPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("nucleus-kotlin")
            pluginManager.apply("org.jetbrains.kotlin.plugin.spring")
            pluginManager.apply("nucleus-spring-test")
        }
}
