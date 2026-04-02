package de.cyanbaz.nucleus.adapter.inbound.web.entry.create

import de.cyanbaz.nucleus.application.entry.command.CreateEntryCommand
import de.cyanbaz.nucleus.application.entry.command.CreateEntryTypeCommand

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
