import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesUseCase
import de.cyanbaz.nucleus.domain.entry.Content
import de.cyanbaz.nucleus.domain.entry.Entry
import de.cyanbaz.nucleus.domain.entry.EntryId
import de.cyanbaz.nucleus.domain.entry.EntryRepository
import de.cyanbaz.nucleus.domain.entry.Tag
import de.cyanbaz.nucleus.domain.entry.Title
import java.time.Clock

class EntryService(
    private val repository: EntryRepository,
    private val clock: Clock,
) : CreateEntryUseCase,
    GetEntryUseCase,
    ListEntriesUseCase {
    override fun create(command: CreateEntryCommand): EntryId {
        val entry =
            Entry.create(
                title = Title(command.title),
                content = Content(command.content),
                type = command.type,
                tags = command.tags.map { Tag(it) }.toSet(),
                clock = clock,
            )

        repository.save(entry)
        return entry.id
    }

    override fun get(id: String): Entry? = repository.findById(EntryId.from(id))

    override fun list(): List<Entry> = repository.findAll()
}
