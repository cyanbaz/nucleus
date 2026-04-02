package de.cyanbaz.nucleus.adapter.inbound.web.entry.list

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesResult
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntryItem
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
                        ListEntryItem(
                            id = "1",
                            title = "Architecture",
                            type = "ARTICLE",
                        ),
                        ListEntryItem(
                            id = "2",
                            title = "Gradle",
                            type = "NOTE",
                        ),
                    ),
            )

        whenever(listEntriesUseCase.list()).thenReturn(result)

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.entries.length()").value(2))
            .andExpect(jsonPath("$.entries[0].id").value("1"))
            .andExpect(jsonPath("$.entries[0].title").value("Architecture"))
            .andExpect(jsonPath("$.entries[1].id").value("2"))
            .andExpect(jsonPath("$.entries[1].title").value("Gradle"))
    }

    @Test
    fun `should return empty list`() {
        whenever(listEntriesUseCase.list())
            .thenReturn(ListEntriesResult(emptyList()))

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.entries.length()").value(0))
    }
}
