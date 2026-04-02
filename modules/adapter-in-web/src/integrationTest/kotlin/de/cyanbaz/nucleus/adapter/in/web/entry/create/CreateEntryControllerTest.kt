package de.cyanbaz.nucleus.adapter.`in`.web.entry.create

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryResult
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryUseCase
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

@WebMvcSliceTest
@Import(CreateEntryController::class)
class CreateEntryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var createEntryUseCase: CreateEntryUseCase

    @Test
    fun `should create entry and return created response`() {
        val result =
            CreateEntryResult(
                id = "11111111-1111-1111-1111-111111111111",
            )

        whenever(createEntryUseCase.create(any())).thenReturn(result)

        val request =
            CreateEntryRequest(
                title = "Architecture",
                content = "Hexagonal architecture with modular Gradle setup",
                type = CreateEntryType.ARTICLE,
                tags = setOf("kotlin", "gradle"),
            )

        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/entries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)),
            ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(result.id))
    }
}
