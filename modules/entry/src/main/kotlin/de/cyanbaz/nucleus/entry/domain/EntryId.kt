package de.cyanbaz.nucleus.entry.domain

import java.util.UUID

@JvmInline
value class EntryId(
    val value: UUID,
) {
    init {
        require(value != UUID(0, 0)) { "EntryId must not be empty" }
    }

    override fun toString(): String = value.toString()

    companion object {
        fun new(): EntryId = EntryId(UUID.randomUUID())

        fun from(value: String): EntryId = EntryId(UUID.fromString(value))
    }
}
