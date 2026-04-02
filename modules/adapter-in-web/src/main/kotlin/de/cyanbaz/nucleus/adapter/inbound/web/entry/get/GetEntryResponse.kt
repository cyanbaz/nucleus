package de.cyanbaz.nucleus.adapter.inbound.web.entry.get

data class GetEntryResponse(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val tags: Set<String>,
)
