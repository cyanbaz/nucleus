package de.cyanbaz.nucleus.domain.entry

interface EntryRepository {
    fun save(entry: Entry): Entry

    fun findById(id: EntryId): Entry?

    fun findAll(): List<Entry>

    fun deleteById(id: EntryId)
}
