package de.cyanbaz.nucleus.entry.adapter.outbound.memory

import de.cyanbaz.nucleus.entry.application.port.outbound.EntryRepository
import de.cyanbaz.nucleus.entry.domain.Entry
import de.cyanbaz.nucleus.entry.domain.EntryId
import java.util.concurrent.ConcurrentHashMap

class InMemoryEntryRepository : EntryRepository {
    private val storage = ConcurrentHashMap<EntryId, Entry>()

    override fun save(entry: Entry): Entry {
        storage[entry.id] = entry
        return entry
    }

    override fun findById(id: EntryId): Entry? = storage[id]

    override fun findAll(): List<Entry> = storage.values.toList()

    override fun deleteById(id: EntryId) {
        storage.remove(id)
    }
}
