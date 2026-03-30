package de.cyanbaz.nucleus.domain.entry

import java.time.Clock
import java.time.Instant

class Entry private constructor(
    val id: EntryId,
    val title: Title,
    val content: Content,
    val type: EntryType,
    tags: Set<Tag>,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    private val _tags: MutableSet<Tag> =
        tags
            .map { it.normalized() }
            .toMutableSet()

    val tags: Set<Tag>
        get() = _tags.toSet()

    fun rename(
        newTitle: Title,
        clock: Clock,
    ): Entry =
        Entry(
            id = id,
            title = newTitle,
            content = content,
            type = type,
            tags = _tags,
            createdAt = createdAt,
            updatedAt = Instant.now(clock),
        )

    fun changeContent(
        newContent: Content,
        clock: Clock,
    ): Entry =
        Entry(
            id = id,
            title = title,
            content = newContent,
            type = type,
            tags = _tags,
            createdAt = createdAt,
            updatedAt = Instant.now(clock),
        )

    fun addTag(
        tag: Tag,
        clock: Clock,
    ): Entry {
        val normalizedTags = _tags.toMutableSet()
        normalizedTags.add(tag.normalized())

        return Entry(
            id = id,
            title = title,
            content = content,
            type = type,
            tags = normalizedTags,
            createdAt = createdAt,
            updatedAt = Instant.now(clock),
        )
    }

    fun removeTag(
        tag: Tag,
        clock: Clock,
    ): Entry {
        val normalizedTags = _tags.toMutableSet()
        normalizedTags.remove(tag.normalized())

        return Entry(
            id = id,
            title = title,
            content = content,
            type = type,
            tags = normalizedTags,
            createdAt = createdAt,
            updatedAt = Instant.now(clock),
        )
    }

    companion object {
        fun create(
            title: Title,
            content: Content,
            type: EntryType,
            tags: Set<Tag>,
            clock: Clock,
        ): Entry {
            val now = Instant.now(clock)

            return Entry(
                id = EntryId.new(),
                title = title,
                content = content,
                type = type,
                tags = tags,
                createdAt = now,
                updatedAt = now,
            )
        }

        fun restore(
            id: EntryId,
            title: Title,
            content: Content,
            type: EntryType,
            tags: Set<Tag>,
            createdAt: Instant,
            updatedAt: Instant,
        ): Entry =
            Entry(
                id = id,
                title = title,
                content = content,
                type = type,
                tags = tags,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
    }
}
