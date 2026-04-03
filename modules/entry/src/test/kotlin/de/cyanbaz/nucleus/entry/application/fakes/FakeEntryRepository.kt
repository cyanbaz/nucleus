package de.cyanbaz.nucleus.entry.application.fakes

import de.cyanbaz.nucleus.entry.application.port.outbound.EntryRepository
import de.cyanbaz.nucleus.entry.domain.Entry
import de.cyanbaz.nucleus.entry.domain.EntryId

class FakeEntryRepository : EntryRepository {
    private val storage = linkedMapOf<EntryId, Entry>()

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
