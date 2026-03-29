package de.cyanbaz.conventions

plugins { id("com.diffplug.spotless") }

spotless {
    kotlin {
        ktlint()
        target("src/**/*.kt", "build-logic/src/**/*.kt")
        targetExclude("**/build/**")
    }
    kotlinGradle {
        ktlint()
        target("**/*.gradle.kts", "build-logic/src/**/*.gradle.kts")
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
