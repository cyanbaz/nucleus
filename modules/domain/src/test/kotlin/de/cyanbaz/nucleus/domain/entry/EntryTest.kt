package de.cyanbaz.nucleus.domain.entry

import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EntryTest {
    private val initialInstant = Instant.parse("2026-03-31T10:00:00Z")
    private val initialClock = Clock.fixed(initialInstant, ZoneOffset.UTC)

    @Test
    fun `should create entry with same created and updated timestamps`() {
        val entry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Clean architecture"),
                type = EntryType.NOTE,
                tags = setOf(Tag("Kotlin")),
                clock = initialClock,
            )

        assertThat(entry.id).isNotNull
        assertThat(entry.title.value).isEqualTo("Architecture")
        assertThat(entry.content.value).isEqualTo("Clean architecture")
        assertThat(entry.type).isEqualTo(EntryType.NOTE)
        assertThat(entry.createdAt).isEqualTo(initialInstant)
        assertThat(entry.updatedAt).isEqualTo(initialInstant)
        assertThat(entry.tags.map { it.value }).containsExactly("kotlin")
    }

    @Test
    fun `should rename entry and update timestamp`() {
        val entry =
            Entry.create(
                title = Title("Old title"),
                content = Content("Clean architecture"),
                type = EntryType.NOTE,
                tags = emptySet(),
                clock = initialClock,
            )

        val updatedInstant = Instant.parse("2026-03-31T11:00:00Z")
        val updatedClock = Clock.fixed(updatedInstant, ZoneOffset.UTC)

        val renamedEntry =
            entry.rename(
                newTitle = Title("New title"),
                clock = updatedClock,
            )

        assertThat(renamedEntry.id).isEqualTo(entry.id)
        assertThat(renamedEntry.title.value).isEqualTo("New title")
        assertThat(renamedEntry.content).isEqualTo(entry.content)
        assertThat(renamedEntry.type).isEqualTo(entry.type)
        assertThat(renamedEntry.tags).isEqualTo(entry.tags)
        assertThat(renamedEntry.createdAt).isEqualTo(entry.createdAt)
        assertThat(renamedEntry.updatedAt).isEqualTo(updatedInstant)
    }

    @Test
    fun `should change content and update timestamp`() {
        val entry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Old content"),
                type = EntryType.NOTE,
                tags = emptySet(),
                clock = initialClock,
            )

        val updatedInstant = Instant.parse("2026-03-31T12:00:00Z")
        val updatedClock = Clock.fixed(updatedInstant, ZoneOffset.UTC)

        val updatedEntry =
            entry.changeContent(
                newContent = Content("New content"),
                clock = updatedClock,
            )

        assertThat(updatedEntry.id).isEqualTo(entry.id)
        assertThat(updatedEntry.title).isEqualTo(entry.title)
        assertThat(updatedEntry.content.value).isEqualTo("New content")
        assertThat(updatedEntry.type).isEqualTo(entry.type)
        assertThat(updatedEntry.tags).isEqualTo(entry.tags)
        assertThat(updatedEntry.createdAt).isEqualTo(entry.createdAt)
        assertThat(updatedEntry.updatedAt).isEqualTo(updatedInstant)
    }

    @Test
    fun `should add normalized tag and update timestamp`() {
        val entry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Content"),
                type = EntryType.NOTE,
                tags = emptySet(),
                clock = initialClock,
            )

        val updatedInstant = Instant.parse("2026-03-31T13:00:00Z")
        val updatedClock = Clock.fixed(updatedInstant, ZoneOffset.UTC)

        val updatedEntry =
            entry.addTag(
                tag = Tag(" Kotlin "),
                clock = updatedClock,
            )

        assertThat(updatedEntry.tags.map { it.value }).containsExactly("kotlin")
        assertThat(updatedEntry.updatedAt).isEqualTo(updatedInstant)
        assertThat(updatedEntry.createdAt).isEqualTo(entry.createdAt)
    }

    @Test
    fun `should not duplicate normalized tags`() {
        val entry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Content"),
                type = EntryType.NOTE,
                tags = setOf(Tag("kotlin")),
                clock = initialClock,
            )

        val updatedEntry =
            entry.addTag(
                tag = Tag(" Kotlin "),
                clock = initialClock,
            )

        assertThat(updatedEntry.tags.map { it.value }).containsExactly("kotlin")
    }

    @Test
    fun `should remove normalized tag and update timestamp`() {
        val entry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Content"),
                type = EntryType.NOTE,
                tags = setOf(Tag("kotlin"), Tag("spring")),
                clock = initialClock,
            )

        val updatedInstant = Instant.parse("2026-03-31T14:00:00Z")
        val updatedClock = Clock.fixed(updatedInstant, ZoneOffset.UTC)

        val updatedEntry =
            entry.removeTag(
                tag = Tag(" Kotlin "),
                clock = updatedClock,
            )

        assertThat(updatedEntry.tags.map { it.value }).containsExactly("spring")
        assertThat(updatedEntry.updatedAt).isEqualTo(updatedInstant)
        assertThat(updatedEntry.createdAt).isEqualTo(entry.createdAt)
    }

    @Test
    fun `should restore entry with given values`() {
        val id = EntryId.new()
        val createdAt = Instant.parse("2026-03-30T10:00:00Z")
        val updatedAt = Instant.parse("2026-03-31T10:00:00Z")

        val entry =
            Entry.restore(
                id = id,
                title = Title("Architecture"),
                content = Content("Restored content"),
                type = EntryType.REFERENCE,
                tags = setOf(Tag(" Kotlin "), Tag(" Spring ")),
                createdAt = createdAt,
                updatedAt = updatedAt,
            )

        assertThat(entry.id).isEqualTo(id)
        assertThat(entry.title.value).isEqualTo("Architecture")
        assertThat(entry.content.value).isEqualTo("Restored content")
        assertThat(entry.type).isEqualTo(EntryType.REFERENCE)
        assertThat(entry.tags.map { it.value }).containsExactlyInAnyOrder("kotlin", "spring")
        assertThat(entry.createdAt).isEqualTo(createdAt)
        assertThat(entry.updatedAt).isEqualTo(updatedAt)
    }
}
