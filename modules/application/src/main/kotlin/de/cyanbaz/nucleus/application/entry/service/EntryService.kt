package de.cyanbaz.nucleus.application.entry.service

import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand
import de.cyanbaz.nucleus.application.entry.command.toDomainType
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryResult
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryResult
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesResult
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesUseCase
import de.cyanbaz.nucleus.application.entry.port.out.EntryRepository
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
                    GetEntryResult(
                        id = entry.id.toString(),
                        title = entry.title.value,
                        content = entry.content.value,
                        type = entry.type.name,
                        tags = entry.tags.map { it.value }.toSet(),
                    )
                },
        )
}
