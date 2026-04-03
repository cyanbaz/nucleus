package de.cyanbaz.nucleus.entry.adapter.inbound.web.list

import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryItem

data class ListEntryResponse(
    val entries: List<ListEntryItem>,
)
