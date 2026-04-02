package de.cyanbaz.nucleus.application.entry.port.`in`

interface GetEntryUseCase {
    fun get(id: String): GetEntryResult?
}
