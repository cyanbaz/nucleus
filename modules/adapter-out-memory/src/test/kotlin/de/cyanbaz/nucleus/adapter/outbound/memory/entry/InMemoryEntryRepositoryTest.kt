package de.cyanbaz.nucleus.adapter.outbound.memory.entry

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

class InMemoryEntryRepositoryTest {
    private val clock =
        Clock.fixed(
            Instant.parse("2026-03-31T10:00:00Z"),
            ZoneOffset.UTC,
        )

    private val repository = InMemoryEntryRepository()

    @Test
    fun `should save and find entry by id`() {
        val entry = createEntry()

        repository.save(entry)

        val found = repository.findById(entry.id)

        assertThat(found).isEqualTo(entry)
    }

    @Test
    fun `should return null when entry not found`() {
        val result = repository.findById(EntryId.new())

        assertThat(result).isNull()
    }

    @Test
    fun `should return all saved entries`() {
        val entry1 = createEntry("First")
        val entry2 = createEntry("Second")

        repository.save(entry1)
        repository.save(entry2)

        val all = repository.findAll()

        assertThat(all).containsExactlyInAnyOrder(entry1, entry2)
    }

    @Test
    fun `should delete entry by id`() {
        val entry = createEntry()

        repository.save(entry)
        repository.deleteById(entry.id)

        val result = repository.findById(entry.id)

        assertThat(result).isNull()
    }

    @Test
    fun `should overwrite entry with same id`() {
        val entry = createEntry()

        repository.save(entry)

        val updated =
            entry.changeContent(
                newContent = Content("Updated content"),
                clock = clock,
            )

        repository.save(updated)

        val found = repository.findById(entry.id)

        assertThat(found!!.content.value).isEqualTo("Updated content")
    }

    private fun createEntry(title: String = "Test"): Entry =
        Entry.create(
            title = Title(title),
            content = Content("Content"),
            type = EntryType.NOTE,
            tags = setOf(Tag("kotlin")),
            clock = clock,
        )
}
