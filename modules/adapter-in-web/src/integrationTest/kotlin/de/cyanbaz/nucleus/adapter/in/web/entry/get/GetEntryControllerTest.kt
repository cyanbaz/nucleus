package de.cyanbaz.nucleus.adapter.`in`.web.entry.get

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryResult
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryUseCase
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcSliceTest
@Import(GetEntryController::class)
class GetEntryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var getEntryUseCase: GetEntryUseCase

    @Test
    fun `should return entry when found`() {
        val result =
            GetEntryResult(
                id = "11111111-1111-1111-1111-111111111111",
                title = "Architecture",
                content = "Hexagonal architecture",
                type = "ARTICLE",
                tags = setOf("kotlin", "gradle"),
            )

        whenever(getEntryUseCase.get(result.id)).thenReturn(result)

        mockMvc
            .perform(get("/api/entries/${result.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(result.id))
            .andExpect(jsonPath("$.title").value("Architecture"))
            .andExpect(jsonPath("$.content").value("Hexagonal architecture"))
            .andExpect(jsonPath("$.type").value("ARTICLE"))
    }

    @Test
    fun `should return 404 when entry not found`() {
        whenever(getEntryUseCase.get("11111111-1111-1111-1111-111111111111"))
            .thenReturn(null)

        mockMvc
            .perform(get("/api/entries/11111111-1111-1111-1111-111111111111"))
            .andExpect(status().isNotFound)
    }
}
