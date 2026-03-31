package de.cyanbaz.nucleus.domain.entry

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ContentTest {
    @Test
    fun `should create content when value is valid`() {
        val content = Content("Some content")

        assertThat(content.value).isEqualTo("Some content")
    }

    @Test
    fun `should throw exception when content is blank`() {
        assertThatThrownBy { Content("   ") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Content must not be blank")
    }

    @Test
    fun `should throw exception when content is longer than 20000 characters`() {
        val tooLongContent = "a".repeat(20_001)

        assertThatThrownBy { Content(tooLongContent) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Content must not be longer than 20000 characters")
    }
}
