package de.cyanbaz.nucleus.adapter.`in`.web.entry.get

import de.cyanbaz.nucleus.adapter.`in`.web.entry.EntryResponse
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/entries")
class GetEntryController(
    private val getEntryUseCase: GetEntryUseCase,
) {
    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String,
    ): ResponseEntity<EntryResponse> =
        getEntryUseCase.get(id)?.let { entry ->
            ResponseEntity.ok(
                EntryResponse(
                    id = entry.id,
                    title = entry.title,
                    content = entry.content,
                    type = entry.type,
                    tags = entry.tags,
                ),
            )
        } ?: ResponseEntity.notFound().build()
}
