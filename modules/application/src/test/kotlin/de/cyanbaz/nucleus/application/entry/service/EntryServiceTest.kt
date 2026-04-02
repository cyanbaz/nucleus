package de.cyanbaz.nucleus.application.entry.service

import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand
import de.cyanbaz.nucleus.application.entry.command.CreateEntryTypeCommand
import de.cyanbaz.nucleus.application.entry.port.inbound.CreateEntryResult
import de.cyanbaz.nucleus.application.entry.port.inbound.GetEntryResult
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesResult
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntryItem
import de.cyanbaz.nucleus.application.entry.port.outbound.EntryRepository
import de.cyanbaz.nucleus.domain.entry.Content
import de.cyanbaz.nucleus.domain.entry.Entry
import de.cyanbaz.nucleus.domain.entry.EntryId
import de.cyanbaz.nucleus.domain.entry.EntryType
import de.cyanbaz.nucleus.domain.entry.Tag
import de.cyanbaz.nucleus.domain.entry.Title
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EntryServiceTest {
    private val fixedInstant = Instant.parse("2026-03-31T10:00:00Z")
    private val clock = Clock.fixed(fixedInstant, ZoneOffset.UTC)

    @Test
    fun `should create and save entry`() {
        val repository = FakeEntryRepository()
        val service = EntryService(repository, clock)

        val command =
            CreateEntryCommand(
                title = "Architecture",
                content = "Hexagonal architecture with modular Gradle setup",
                type = CreateEntryTypeCommand.ARTICLE,
                tags = setOf("Kotlin", "Gradle"),
            )

        val result = service.create(command)

        val savedEntry = repository.findById(EntryId.from(result.id))

        assertThat(result).isEqualTo(
            CreateEntryResult(
                id = result.id,
            ),
        )

        assertThat(savedEntry).isNotNull
        assertThat(savedEntry!!.id.toString()).isEqualTo(result.id)
        assertThat(savedEntry.title.value).isEqualTo("Architecture")
        assertThat(savedEntry.content.value).isEqualTo("Hexagonal architecture with modular Gradle setup")
        assertThat(savedEntry.type).isEqualTo(EntryType.ARTICLE)
        assertThat(savedEntry.tags.map { it.value }).containsExactlyInAnyOrder("kotlin", "gradle")
        assertThat(savedEntry.createdAt).isEqualTo(fixedInstant)
        assertThat(savedEntry.updatedAt).isEqualTo(fixedInstant)
    }

    @Test
    fun `should get entry by id`() {
        val repository = FakeEntryRepository()
        val service = EntryService(repository, clock)

        val entry =
            Entry.create(
                title = Title("Nucleus"),
                content = Content("A clean modular Spring Boot example"),
                type = EntryType.NOTE,
                tags = setOf(Tag("Kotlin")),
                clock = clock,
            )

        repository.save(entry)

        val result = service.get(entry.id.toString())

        assertThat(result).isEqualTo(
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
    fun `should list all entries`() {
        val repository = FakeEntryRepository()
        val service = EntryService(repository, clock)

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

        val result = service.list()

        assertThat(result).isEqualTo(
            ListEntriesResult(
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

    @Test
    fun `should return null when entry does not exist`() {
        val repository = FakeEntryRepository()
        val service = EntryService(repository, clock)

        val result = service.get(EntryId.new().toString())

        assertThat(result).isNull()
    }

    private class FakeEntryRepository : EntryRepository {
        private val storage = linkedMapOf<EntryId, Entry>()

        override fun save(entry: Entry): Entry {
            storage[entry.id] = entry
            return entry
        }

        override fun findById(id: EntryId): Entry? = storage[id]

        override fun findAll(): List<Entry> = storage.values.toList()

        override fun deleteById(id: EntryId) {
            storage.remove(id)
        }
    }
}
