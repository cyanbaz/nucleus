package de.cyanbaz.nucleus.entry.adapter.inbound.web.create

data class CreateEntryRequest(
    val title: String,
    val content: String,
    val type: CreateEntryType,
    val tags: Set<String> = emptySet(),
)
