package de.cyanbaz.nucleus.entry.application

import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryItem
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryResult
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryUseCase
import de.cyanbaz.nucleus.entry.application.port.outbound.EntryRepository
import java.time.Clock

class ListEntriesService(
    private val entryRepository: EntryRepository,
    private val clock: Clock,
) : ListEntryUseCase {
    override fun list(): ListEntryResult =
        ListEntryResult(
            entries =
                entryRepository.findAll().map { entry ->
                    ListEntryItem(
                        id = entry.id.toString(),
                        title = entry.title.value,
                        type = entry.type.name,
                    )
                },
        )
}
