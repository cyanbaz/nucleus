package de.cyanbaz.nucleus.adapter.out.memory.entry

import de.cyanbaz.nucleus.domain.entry.Entry
import de.cyanbaz.nucleus.domain.entry.EntryId
import de.cyanbaz.nucleus.domain.entry.EntryRepository
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
