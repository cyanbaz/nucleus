package de.cyanbaz.nucleus.entry.application.command

import de.cyanbaz.nucleus.entry.domain.EntryType

internal fun CreateEntryTypeCommand.toDomainType(): EntryType =
    when (this) {
        CreateEntryTypeCommand.NOTE -> EntryType.NOTE
        CreateEntryTypeCommand.ARTICLE -> EntryType.ARTICLE
        CreateEntryTypeCommand.SNIPPET -> EntryType.SNIPPET
        CreateEntryTypeCommand.REFERENCE -> EntryType.REFERENCE
    }
