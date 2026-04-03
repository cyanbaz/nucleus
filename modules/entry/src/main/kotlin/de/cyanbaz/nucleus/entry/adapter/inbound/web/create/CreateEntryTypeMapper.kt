package de.cyanbaz.nucleus.entry.adapter.inbound.web.create

import de.cyanbaz.nucleus.entry.application.command.CreateEntryCommand
import de.cyanbaz.nucleus.entry.application.command.CreateEntryTypeCommand

fun CreateEntryRequest.toCommand(): CreateEntryCommand =
    CreateEntryCommand(
        title = title,
        content = content,
        type = type.toCommandType(),
        tags = tags,
    )

private fun CreateEntryType.toCommandType(): CreateEntryTypeCommand =
    when (this) {
        CreateEntryType.NOTE -> CreateEntryTypeCommand.NOTE
        CreateEntryType.ARTICLE -> CreateEntryTypeCommand.ARTICLE
        CreateEntryType.SNIPPET -> CreateEntryTypeCommand.SNIPPET
        CreateEntryType.REFERENCE -> CreateEntryTypeCommand.REFERENCE
    }
