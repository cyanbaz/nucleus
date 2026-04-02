package de.cyanbaz.nucleus.adapter.inbound.web.entry.list

import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntryItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/entries")
class ListEntriesController(
    private val listEntriesUseCase: ListEntriesUseCase,
) {
    @GetMapping
    fun getById(): ListEntriesResponse =
        ListEntriesResponse(
            entries =
                listEntriesUseCase.list().entries.map { entry ->
                    ListEntryItem(
                        id = entry.id,
                        title = entry.title,
                        type = entry.type,
                    )
                },
        )
}
