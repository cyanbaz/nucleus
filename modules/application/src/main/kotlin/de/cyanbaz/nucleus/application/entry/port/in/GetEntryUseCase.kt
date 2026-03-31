package de.cyanbaz.nucleus.application.entry.port.`in`

import de.cyanbaz.nucleus.domain.entry.Entry

interface GetEntryUseCase {
    fun get(id: String): Entry?
}
