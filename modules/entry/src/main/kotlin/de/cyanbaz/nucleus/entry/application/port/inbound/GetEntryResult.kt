package de.cyanbaz.nucleus.entry.application.port.inbound

data class GetEntryResult(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val tags: Set<String>,
)
