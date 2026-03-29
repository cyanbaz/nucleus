plugins { `kotlin-dsl` }

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("nucleus-kotlin") {
            id = "nucleus-kotlin"
            implementationClass = "de.cyanbaz.plugins.NucleusKotlinPlugin"
        }
        register("nucleus-spring-boot") {
            id = "nucleus-spring-boot"
            implementationClass = "de.cyanbaz.plugins.NucleusSpringBootPlugin"
        }
        register("nucleus-base") {
            id = "nucleus-base"
            implementationClass = "de.cyanbaz.plugins.NucleusBasePlugin"
        }
    }
}

dependencies {
    implementation(plugin(libs.plugins.kotlin.jvm))
    implementation(plugin(libs.plugins.kotlin.plugin.spring))
    implementation(plugin(libs.plugins.org.springframework.boot))
    implementation(plugin(libs.plugins.io.spring.dependency.management))
    implementation(plugin(libs.plugins.com.diffplug.spotless))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    }
