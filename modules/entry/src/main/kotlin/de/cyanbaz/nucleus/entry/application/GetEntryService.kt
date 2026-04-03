package de.cyanbaz.nucleus.entry.application

import de.cyanbaz.nucleus.entry.application.port.inbound.GetEntryResult
import de.cyanbaz.nucleus.entry.application.port.inbound.GetEntryUseCase
import de.cyanbaz.nucleus.entry.application.port.outbound.EntryRepository
import de.cyanbaz.nucleus.entry.domain.EntryId
import java.time.Clock

class GetEntryService(
    private val entryRepository: EntryRepository,
    private val clock: Clock,
) : GetEntryUseCase {
    override fun get(id: String): GetEntryResult? =
        entryRepository.findById(EntryId.from(id))?.let { entry ->
            GetEntryResult(
                id = entry.id.toString(),
                title = entry.title.value,
                content = entry.content.value,
                type = entry.type.name,
                tags = entry.tags.map { it.value }.toSet(),
            )
        }
}
