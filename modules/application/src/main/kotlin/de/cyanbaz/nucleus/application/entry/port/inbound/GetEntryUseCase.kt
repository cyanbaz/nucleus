package de.cyanbaz.nucleus.application.entry.port.inbound

interface GetEntryUseCase {
    fun get(id: String): GetEntryResult?
}
