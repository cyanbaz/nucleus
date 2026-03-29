package de.cyanbaz.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
abstract class NucleusSpringBootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("org.springframework.boot")
            pluginManager.apply("io.spring.dependency-management")
        }
}
