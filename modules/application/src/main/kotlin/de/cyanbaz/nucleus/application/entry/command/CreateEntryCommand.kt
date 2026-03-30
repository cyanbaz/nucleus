package de.cyanbaz.nucleus.application.entry.command

import de.cyanbaz.nucleus.domain.entry.EntryType

data class CreateEntryCommand(
    val title: String,
    val content: String,
    val type: EntryType,
    val tags: Set<String>,
)
