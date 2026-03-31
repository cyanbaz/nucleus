package de.cyanbaz.nucleus.adapter.`in`.web.entry.list

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesUseCase
import de.cyanbaz.nucleus.domain.entry.Content
import de.cyanbaz.nucleus.domain.entry.Entry
import de.cyanbaz.nucleus.domain.entry.EntryType
import de.cyanbaz.nucleus.domain.entry.Tag
import de.cyanbaz.nucleus.domain.entry.Title
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
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

    private val clock =
        Clock.fixed(
            Instant.parse("2026-03-31T10:00:00Z"),
            ZoneOffset.UTC,
        )

    @Test
    fun `should return list of entries`() {
        val first =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Hexagonal architecture"),
                type = EntryType.ARTICLE,
                tags = setOf(Tag("kotlin")),
                clock = clock,
            )

        val second =
            Entry.create(
                title = Title("Gradle"),
                content = Content("Convention plugins"),
                type = EntryType.NOTE,
                tags = setOf(Tag("build")),
                clock = clock,
            )

        whenever(listEntriesUseCase.list()).thenReturn(listOf(first, second))

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(first.id.toString()))
            .andExpect(jsonPath("$[0].title").value("Architecture"))
            .andExpect(jsonPath("$[1].id").value(second.id.toString()))
            .andExpect(jsonPath("$[1].title").value("Gradle"))
    }

    @Test
    fun `should return empty list`() {
        whenever(listEntriesUseCase.list()).thenReturn(emptyList())

        mockMvc
            .perform(get("/api/entries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(0))
    }
}
