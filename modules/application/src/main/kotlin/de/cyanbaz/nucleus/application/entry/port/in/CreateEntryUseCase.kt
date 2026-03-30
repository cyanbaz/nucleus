package de.cyanbaz.nucleus.application.entry.port.`in`

import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand
import de.cyanbaz.nucleus.domain.entry.EntryId

interface CreateEntryUseCase {
    fun create(command: CreateEntryCommand): EntryId
}
