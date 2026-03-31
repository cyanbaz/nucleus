package de.cyanbaz.nucleus.application.entry.port.`in`

import de.cyanbaz.nucleus.domain.entry.Entry

interface ListEntriesUseCase {
    fun list(): List<Entry>
}
