package de.cyanbaz.nucleus.entry.adapter.inbound.web.create

import de.cyanbaz.nucleus.entry.application.port.inbound.CreateEntryUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/entries")
class CreateEntryController(
    private val createEntryUseCase: CreateEntryUseCase,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: CreateEntryRequest,
    ): CreateEntryResponse {
        val command = request.toCommand()

        val result = createEntryUseCase.create(command)

        return CreateEntryResponse(
            id = result.id,
        )
    }
}
