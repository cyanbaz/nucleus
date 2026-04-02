package de.cyanbaz.nucleus.application.entry.port.outbound

import de.cyanbaz.nucleus.domain.entry.Entry
import de.cyanbaz.nucleus.domain.entry.EntryId

interface EntryRepository {
    fun save(entry: Entry): Entry

    fun findById(id: EntryId): Entry?

    fun findAll(): List<Entry>

    fun deleteById(id: EntryId)
}
