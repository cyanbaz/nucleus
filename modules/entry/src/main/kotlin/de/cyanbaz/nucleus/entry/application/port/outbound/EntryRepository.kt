package de.cyanbaz.nucleus.entry.application.port.outbound

import de.cyanbaz.nucleus.entry.domain.Entry
import de.cyanbaz.nucleus.entry.domain.EntryId

interface EntryRepository {
    fun save(entry: Entry): Entry

    fun findById(id: EntryId): Entry?

    fun findAll(): List<Entry>

    fun deleteById(id: EntryId)
}
