package de.cyanbaz.nucleus.entry.application.port.inbound

import de.cyanbaz.nucleus.entry.application.command.CreateEntryCommand

interface CreateEntryUseCase {
    fun create(command: CreateEntryCommand): CreateEntryResult
}
