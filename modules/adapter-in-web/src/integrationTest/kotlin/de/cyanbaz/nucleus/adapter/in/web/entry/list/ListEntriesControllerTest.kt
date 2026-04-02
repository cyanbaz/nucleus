package de.cyanbaz.nucleus.adapter.`in`.web.entry.list

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryResult
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesResult
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesUseCase
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
@Import(ListEntriesController::class)
class ListEntriesControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var listEntriesUseCase: ListEntriesUseCase

    @Test
    fun `should return list of entries`() {
        val result =
            ListEntriesResult(
                entries =
                    listOf(
                        GetEntryResult(
                            id = "1",
                            title = "Architecture",
                            content = "Hexagonal architecture",
                            type = "ARTICLE",
                            tags = setOf("kotlin", "gradle"),
                        ),
                        GetEntryResult(
                            id = "2",
                            title = "Gradle",
                            content = "Convention plugins and version catalogs",
                            type = "NOTE",
                            tags = setOf("gradle"),
                        ),
                    ),
            )

        whenever(listEntriesUseCase.list()).thenReturn(result)

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Architecture"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].title").value("Gradle"))
    }

    @Test
    fun `should return empty list`() {
        whenever(listEntriesUseCase.list())
            .thenReturn(ListEntriesResult(emptyList()))

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(0))
    }
}
