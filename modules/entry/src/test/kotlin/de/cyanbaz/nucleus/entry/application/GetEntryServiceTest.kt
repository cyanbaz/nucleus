package de.cyanbaz.nucleus.entry.application

import de.cyanbaz.nucleus.entry.application.fakes.FakeEntryRepository
import de.cyanbaz.nucleus.entry.application.port.inbound.GetEntryResult
import de.cyanbaz.nucleus.entry.domain.Content
import de.cyanbaz.nucleus.entry.domain.Entry
import de.cyanbaz.nucleus.entry.domain.EntryId
import de.cyanbaz.nucleus.entry.domain.EntryType
import de.cyanbaz.nucleus.entry.domain.Tag
import de.cyanbaz.nucleus.entry.domain.Title
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GetEntryServiceTest {
    private val fixedInstant = Instant.parse("2026-03-31T10:00:00Z")
    private val clock = Clock.fixed(fixedInstant, ZoneOffset.UTC)

    @Test
    fun `should get entry by id`() {
        val repository = FakeEntryRepository()
        val getEntryService = GetEntryService(repository, clock)

        val entry =
            Entry.create(
                title = Title("Nucleus"),
                content = Content("A clean modular Spring Boot example"),
                type = EntryType.NOTE,
                tags = setOf(Tag("Kotlin")),
                clock = clock,
            )

        repository.save(entry)

        val result = getEntryService.get(entry.id.toString())

        Assertions.assertThat(result).isEqualTo(
            GetEntryResult(
                id = entry.id.toString(),
                title = "Nucleus",
                content = "A clean modular Spring Boot example",
                type = "NOTE",
                tags = setOf("kotlin"),
            ),
        )
    }

    @Test
    fun `should return null when entry does not exist`() {
        val repository = FakeEntryRepository()
        val getEntryService = GetEntryService(repository, clock)

        val result = getEntryService.get(EntryId.new().toString())

        Assertions.assertThat(result).isNull()
    }
}
