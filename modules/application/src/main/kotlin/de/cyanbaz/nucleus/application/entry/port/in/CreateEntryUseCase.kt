package de.cyanbaz.nucleus.application.entry.port.`in`

import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand

interface CreateEntryUseCase {
    fun create(command: CreateEntryCommand): CreateEntryResult
}
