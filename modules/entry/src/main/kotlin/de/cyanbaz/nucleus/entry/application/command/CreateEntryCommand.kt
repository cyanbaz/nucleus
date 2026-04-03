package de.cyanbaz.nucleus.entry.application.command

data class CreateEntryCommand(
    val title: String,
    val content: String,
    val type: CreateEntryTypeCommand,
    val tags: Set<String>,
)
