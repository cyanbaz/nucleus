plugins { `kotlin-dsl` }

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("nucleus-spotless") {
            id = "nucleus-spotless"
            implementationClass = "NucleusSpotlessPlugin"
        }
        register("nucleus-kotlin") {
            id = "nucleus-kotlin"
            implementationClass = "NucleusKotlinPlugin"
        }
        register("nucleus-spring") {
            id = "nucleus-spring"
            implementationClass = "NucleusSpringPlugin"
        }
        register("nucleus-test") {
            id = "nucleus-test"
            implementationClass = "NucleusTestPlugin"
        }
        register("nucleus-spring-boot") {
            id = "nucleus-spring-boot"
            implementationClass = "NucleusSpringBootPlugin"
        }
        register("nucleus-spring-test") {
            id = "nucleus-spring-test"
            implementationClass = "NucleusSpringTestPlugin"
        }
        register("nucleus-jacoco") {
            id = "nucleus-jacoco"
            implementationClass = "NucleusJacocoPlugin"
        }
    }
}

dependencies {
    implementation(plugin(libs.plugins.kotlin.jvm))
    implementation(plugin(libs.plugins.kotlin.plugin.spring))
    implementation(plugin(libs.plugins.spring.boot))
    implementation(plugin(libs.plugins.spring.dependency.management))
    implementation(plugin(libs.plugins.spotless))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    }
