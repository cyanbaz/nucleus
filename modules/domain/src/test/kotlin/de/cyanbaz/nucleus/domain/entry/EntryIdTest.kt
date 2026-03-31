package de.cyanbaz.nucleus.domain.entry

import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class EntryIdTest {
    @Test
    fun `should create entry id from uuid`() {
        val uuid = UUID.randomUUID()

        val entryId = EntryId(uuid)

        assertThat(entryId.value).isEqualTo(uuid)
    }

    @Test
    fun `should throw exception when uuid is empty`() {
        val emptyUuid = UUID(0, 0)

        assertThatThrownBy { EntryId(emptyUuid) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("EntryId must not be empty")
    }

    @Test
    fun `should create new entry id`() {
        val entryId = EntryId.new()

        assertThat(entryId.value).isNotNull
        assertThat(entryId.value).isNotEqualTo(UUID(0, 0))
    }

    @Test
    fun `should create entry id from string`() {
        val uuid = UUID.randomUUID()

        val entryId = EntryId.from(uuid.toString())

        assertThat(entryId.value).isEqualTo(uuid)
    }
}
