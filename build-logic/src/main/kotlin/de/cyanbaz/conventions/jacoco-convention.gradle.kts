package de.cyanbaz.conventions

plugins { jacoco }

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"), tasks.named("integrationTest"))

    executionData.setFrom(fileTree(layout.buildDirectory.dir("jacoco")) { include("*.exec") })

    classDirectories.setFrom(
        files(
            fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
                exclude("**/Nucleus*.class")
                exclude("**/generated/**")
            },
            fileTree(layout.buildDirectory.dir("classes/java/main")) { exclude("**/generated/**") },
        ),
    )

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.named("test"), tasks.named("integrationTest"))
    finalizedBy(tasks.named("jacocoTestReport"))

    executionData.setFrom(fileTree(layout.buildDirectory.dir("jacoco")) { include("*.exec") })

    classDirectories.setFrom(
        files(
            fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
                exclude("**/Nucleus*.class")
                exclude("**/generated/**")
            },
            fileTree(layout.buildDirectory.dir("classes/java/main")) { exclude("**/generated/**") },
        ),
    )

    violationRules { rule { limit { minimum = "0.5".toBigDecimal() } } }
}
