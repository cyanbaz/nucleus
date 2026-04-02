package de.cyanbaz.nucleus.adapter.`in`.web.entry.create

data class CreateEntryRequest(
    val title: String,
    val content: String,
    val type: CreateEntryType,
    val tags: Set<String> = emptySet(),
)
