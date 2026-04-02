package de.cyanbaz.nucleus.application.entry.service

import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand
import de.cyanbaz.nucleus.application.entry.command.toDomainType
import de.cyanbaz.nucleus.application.entry.port.inbound.CreateEntryResult
import de.cyanbaz.nucleus.application.entry.port.inbound.CreateEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.GetEntryResult
import de.cyanbaz.nucleus.application.entry.port.inbound.GetEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesResult
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntryItem
import de.cyanbaz.nucleus.application.entry.port.outbound.EntryRepository
import de.cyanbaz.nucleus.domain.entry.Content
import de.cyanbaz.nucleus.domain.entry.Entry
import de.cyanbaz.nucleus.domain.entry.EntryId
import de.cyanbaz.nucleus.domain.entry.Tag
import de.cyanbaz.nucleus.domain.entry.Title
import java.time.Clock

class EntryService(
    private val repository: EntryRepository,
    private val clock: Clock,
) : CreateEntryUseCase,
    GetEntryUseCase,
    ListEntriesUseCase {
    override fun create(command: CreateEntryCommand): CreateEntryResult {
        val entry =
            Entry.create(
                title = Title(command.title),
                content = Content(command.content),
                type = command.type.toDomainType(),
                tags = command.tags.map { Tag(it) }.toSet(),
                clock = clock,
            )

        repository.save(entry)
        return CreateEntryResult(entry.id.toString())
    }

    override fun get(id: String): GetEntryResult? =
        repository.findById(EntryId.from(id))?.let { entry ->
            GetEntryResult(
                id = entry.id.toString(),
                title = entry.title.value,
                content = entry.content.value,
                type = entry.type.name,
                tags = entry.tags.map { it.value }.toSet(),
            )
        }

    override fun list(): ListEntriesResult =
        ListEntriesResult(
            entries =
                repository.findAll().map { entry ->
                    ListEntryItem(
                        id = entry.id.toString(),
                        title = entry.title.value,
                        type = entry.type.name,
                    )
                },
        )
}
