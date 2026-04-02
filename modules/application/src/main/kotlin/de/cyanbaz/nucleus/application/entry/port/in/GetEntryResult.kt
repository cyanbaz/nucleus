package de.cyanbaz.nucleus.application.entry.port.`in`

data class GetEntryResult(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val tags: Set<String>,
)
