package de.cyanbaz.nucleus.adapter.`in`.web.entry.get

import de.cyanbaz.nucleus.WebMvcSliceTest
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryUseCase
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
@Import(GetEntryController::class)
class GetEntryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var getEntryUseCase: GetEntryUseCase

    private val clock =
        Clock.fixed(
            Instant.parse("2026-03-31T10:00:00Z"),
            ZoneOffset.UTC,
        )

    @Test
    fun `should return entry when found`() {
        val entry =
            Entry.create(
                title = Title("Architecture"),
                content = Content("Hexagonal architecture"),
                type = EntryType.ARTICLE,
                tags = setOf(Tag("kotlin"), Tag("gradle")),
                clock = clock,
            )

        whenever(getEntryUseCase.get(entry.id.toString())).thenReturn(entry)

        mockMvc
            .perform(get("/api/entries/${entry.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(entry.id.toString()))
            .andExpect(jsonPath("$.title").value("Architecture"))
            .andExpect(jsonPath("$.content").value("Hexagonal architecture"))
            .andExpect(jsonPath("$.type").value("ARTICLE"))
            .andExpect(jsonPath("$.tags[0]").value("kotlin"))
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
