package de.cyanbaz.nucleus.entry.adapter.inbound.web.get

data class GetEntryResponse(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val tags: Set<String>,
)
