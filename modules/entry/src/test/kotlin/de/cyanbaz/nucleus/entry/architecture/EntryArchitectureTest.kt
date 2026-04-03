package de.cyanbaz.nucleus.entry.architecture

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

class EntryArchitectureTest {
    companion object {
        private const val PACKAGE_NAME = "de.cyanbaz.nucleus.entry"

        private val importedClasses =
            ClassFileImporter()
                .importPackages(PACKAGE_NAME)
    }

    @Test
    fun `domain should not depend on application`() {
        noClasses()
            .that()
            .resideInAPackage("..domain..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..application..")
            .check(importedClasses)
    }

    @Test
    fun `domain and application should not depend on adapter`() {
        noClasses()
            .that()
            .resideInAPackage("..domain..")
            .or()
            .resideInAPackage("..application..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter..")
            .check(importedClasses)
    }

    @Test
    fun `only adapters should depend on spring or jpa`() {
        noClasses()
            .that()
            .resideOutsideOfPackage("..adapter..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "org.springframework..",
                "jakarta.persistence..",
            ).check(importedClasses)
    }

    @Test
    fun `adapter in should not depend on adapter out`() {
        noClasses()
            .that()
            .resideInAPackage("..adapter.inbound..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter.outbound..")
            .check(importedClasses)
    }

    @Test
    fun `adapter out should not depend on adapter in`() {
        noClasses()
            .that()
            .resideInAPackage("..adapter.outbound..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter.inbound..")
            .check(importedClasses)
    }
}
