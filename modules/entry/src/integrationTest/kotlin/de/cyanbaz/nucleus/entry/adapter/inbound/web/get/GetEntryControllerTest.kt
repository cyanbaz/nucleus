package de.cyanbaz.nucleus.entry.adapter.inbound.web.get

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.entry.application.port.inbound.GetEntryResult
import de.cyanbaz.nucleus.entry.application.port.inbound.GetEntryUseCase
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
            .perform(MockMvcRequestBuilders.get("/api/entries/${result.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(result.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Architecture"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Hexagonal architecture"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("ARTICLE"))
    }

    @Test
    fun `should return 404 when entry not found`() {
        whenever(getEntryUseCase.get("11111111-1111-1111-1111-111111111111"))
            .thenReturn(null)

        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/entries/11111111-1111-1111-1111-111111111111"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
