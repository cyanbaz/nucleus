package de.cyanbaz.nucleus.application.entry.command

data class CreateEntryCommand(
    val title: String,
    val content: String,
    val type: CreateEntryTypeCommand,
    val tags: Set<String>,
)
