package de.cyanbaz.nucleus.entry.application

import de.cyanbaz.nucleus.entry.application.command.CreateEntryCommand
import de.cyanbaz.nucleus.entry.application.command.toDomainType
import de.cyanbaz.nucleus.entry.application.port.inbound.CreateEntryResult
import de.cyanbaz.nucleus.entry.application.port.inbound.CreateEntryUseCase
import de.cyanbaz.nucleus.entry.application.port.outbound.EntryRepository
import de.cyanbaz.nucleus.entry.domain.Content
import de.cyanbaz.nucleus.entry.domain.Entry
import de.cyanbaz.nucleus.entry.domain.Tag
import de.cyanbaz.nucleus.entry.domain.Title
import java.time.Clock

class CreateEntryService(
    private val entryRepository: EntryRepository,
    private val clock: Clock,
) : CreateEntryUseCase {
    override fun create(command: CreateEntryCommand): CreateEntryResult {
        val entry =
            Entry.create(
                title = Title(command.title),
                content = Content(command.content),
                type = command.type.toDomainType(),
                tags = command.tags.map { Tag(it) }.toSet(),
                clock = clock,
            )

        entryRepository.save(entry)
        return CreateEntryResult(entry.id.toString())
    }
}
