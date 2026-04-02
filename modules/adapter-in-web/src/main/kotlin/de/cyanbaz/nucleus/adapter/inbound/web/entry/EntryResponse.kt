package de.cyanbaz.nucleus.adapter.inbound.web.entry

data class EntryResponse(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val tags: Set<String>,
)
