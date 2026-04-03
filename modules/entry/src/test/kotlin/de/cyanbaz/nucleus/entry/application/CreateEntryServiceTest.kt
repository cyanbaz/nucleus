package de.cyanbaz.nucleus.entry.application

import de.cyanbaz.nucleus.entry.application.command.CreateEntryCommand
import de.cyanbaz.nucleus.entry.application.command.CreateEntryTypeCommand
import de.cyanbaz.nucleus.entry.application.fakes.FakeEntryRepository
import de.cyanbaz.nucleus.entry.application.port.inbound.CreateEntryResult
import de.cyanbaz.nucleus.entry.domain.EntryId
import de.cyanbaz.nucleus.entry.domain.EntryType
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CreateEntryServiceTest {
    private val fixedInstant = Instant.parse("2026-03-31T10:00:00Z")
    private val clock = Clock.fixed(fixedInstant, ZoneOffset.UTC)

    @Test
    fun `should create and save entry`() {
        val repository = FakeEntryRepository()
        val createEntryService = CreateEntryService(repository, clock)

        val command =
            CreateEntryCommand(
                title = "Architecture",
                content = "Hexagonal architecture with modular Gradle setup",
                type = CreateEntryTypeCommand.ARTICLE,
                tags = setOf("Kotlin", "Gradle"),
            )

        val result = createEntryService.create(command)

        val savedEntry = repository.findById(EntryId.from(result.id))

        Assertions.assertThat(result).isEqualTo(
            CreateEntryResult(
                id = result.id,
            ),
        )

        Assertions.assertThat(savedEntry).isNotNull
        Assertions.assertThat(savedEntry!!.id.toString()).isEqualTo(result.id)
        Assertions.assertThat(savedEntry.title.value).isEqualTo("Architecture")
        Assertions.assertThat(savedEntry.content.value).isEqualTo("Hexagonal architecture with modular Gradle setup")
        Assertions.assertThat(savedEntry.type).isEqualTo(EntryType.ARTICLE)
        Assertions.assertThat(savedEntry.tags.map { it.value }).containsExactlyInAnyOrder("kotlin", "gradle")
        Assertions.assertThat(savedEntry.createdAt).isEqualTo(fixedInstant)
        Assertions.assertThat(savedEntry.updatedAt).isEqualTo(fixedInstant)
    }
}
