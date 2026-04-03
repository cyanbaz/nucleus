package de.cyanbaz.nucleus.entry.adapter.inbound.web.list

import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryItem
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/entries")
class ListEntryController(
    private val listEntryUseCase: ListEntryUseCase,
) {
    @GetMapping
    fun getById(): ListEntryResponse =
        ListEntryResponse(
            entries =
                listEntryUseCase.list().entries.map { entry ->
                    ListEntryItem(
                        id = entry.id,
                        title = entry.title,
                        type = entry.type,
                    )
                },
        )
}
