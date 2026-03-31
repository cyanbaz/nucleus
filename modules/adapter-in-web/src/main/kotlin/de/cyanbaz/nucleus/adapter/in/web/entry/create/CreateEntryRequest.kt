package de.cyanbaz.nucleus.adapter.`in`.web.entry.create

import de.cyanbaz.nucleus.domain.entry.EntryType

data class CreateEntryRequest(
    val title: String,
    val content: String,
    val type: EntryType,
    val tags: Set<String> = emptySet(),
)
