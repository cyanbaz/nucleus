package de.cyanbaz.nucleus.adapter.`in`.web.entry.list

import de.cyanbaz.nucleus.adapter.`in`.web.entry.EntryResponse
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/entries")
class ListEntriesController(
    private val listEntriesUseCase: ListEntriesUseCase,
) {
    @GetMapping
    fun getById(): List<EntryResponse> =
        listEntriesUseCase.list().map { entry ->
            EntryResponse(
                id = entry.id.toString(),
                title = entry.title.value,
                content = entry.content.value,
                type = entry.type.name,
                tags = entry.tags.map { it.value }.toSet(),
            )
        }
}
