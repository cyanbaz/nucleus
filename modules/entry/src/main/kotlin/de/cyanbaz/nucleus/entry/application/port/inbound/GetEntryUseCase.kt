package de.cyanbaz.nucleus.entry.application.port.inbound

interface GetEntryUseCase {
    fun get(id: String): GetEntryResult?
}
