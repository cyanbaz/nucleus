package de.cyanbaz.nucleus.entry.application

import de.cyanbaz.nucleus.entry.application.fakes.FakeEntryRepository
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryItem
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryResult
import de.cyanbaz.nucleus.entry.domain.Content
import de.cyanbaz.nucleus.entry.domain.Entry
import de.cyanbaz.nucleus.entry.domain.EntryType
import de.cyanbaz.nucleus.entry.domain.Tag
import de.cyanbaz.nucleus.entry.domain.Title
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ListEntriesServiceTest {
    private val fixedInstant = Instant.parse("2026-03-31T10:00:00Z")
    private val clock = Clock.fixed(fixedInstant, ZoneOffset.UTC)

    @Test
    fun `should list all entries`() {
        val repository = FakeEntryRepository()
        val listEntriesService = ListEntriesService(repository, clock)

        val firstEntry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Hexagonal architecture"),
                type = EntryType.ARTICLE,
                tags = setOf(Tag("architecture")),
                clock = clock,
            )

        val secondEntry =
            Entry.create(
                title = Title("Gradle"),
                content = Content("Convention plugins and version catalogs"),
                type = EntryType.NOTE,
                tags = setOf(Tag("gradle")),
                clock = clock,
            )

        repository.save(firstEntry)
        repository.save(secondEntry)

        val result = listEntriesService.list()

        Assertions.assertThat(result).isEqualTo(
            ListEntryResult(
                entries =
                    listOf(
                        ListEntryItem(
                            id = firstEntry.id.toString(),
                            title = "Architecture",
                            type = "ARTICLE",
                        ),
                        ListEntryItem(
                            id = secondEntry.id.toString(),
                            title = "Gradle",
                            type = "NOTE",
                        ),
                    ),
            ),
        )
    }
}
