package de.cyanbaz.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
abstract class NucleusBasePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            pluginManager.apply("nucleus-kotlin")
            pluginManager.apply("nucleus-spring-boot")
            pluginManager.apply("de.cyanbaz.conventions.test-convention")
            pluginManager.apply("de.cyanbaz.conventions.jacoco-convention")
            pluginManager.apply("de.cyanbaz.conventions.spotless-convention")
        }
}
