package de.cyanbaz.nucleus.adapter.inbound.web.entry.list

import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntryItem

data class ListEntriesResponse(
    val entries: List<ListEntryItem>,
)
