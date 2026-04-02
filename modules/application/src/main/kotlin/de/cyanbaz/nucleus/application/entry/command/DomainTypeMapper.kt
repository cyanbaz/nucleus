package de.cyanbaz.nucleus.application.entry.command

import de.cyanbaz.nucleus.domain.entry.EntryType

internal fun CreateEntryTypeCommand.toDomainType(): EntryType =
    when (this) {
        CreateEntryTypeCommand.NOTE -> EntryType.NOTE
        CreateEntryTypeCommand.ARTICLE -> EntryType.ARTICLE
        CreateEntryTypeCommand.SNIPPET -> EntryType.SNIPPET
        CreateEntryTypeCommand.REFERENCE -> EntryType.REFERENCE
    }
