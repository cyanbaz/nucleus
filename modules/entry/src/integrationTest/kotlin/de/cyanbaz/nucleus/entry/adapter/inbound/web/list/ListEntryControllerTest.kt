package de.cyanbaz.nucleus.entry.adapter.inbound.web.list

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryItem
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryResult
import de.cyanbaz.nucleus.entry.application.port.inbound.ListEntryUseCase
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
@Import(ListEntryController::class)
class ListEntryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var listEntryUseCase: ListEntryUseCase

    @Test
    fun `should return list of entries`() {
        val result =
            ListEntryResult(
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

        whenever(listEntryUseCase.list()).thenReturn(result)

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
        whenever(listEntryUseCase.list())
            .thenReturn(ListEntryResult(emptyList()))

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.entries.length()").value(0))
    }
}
