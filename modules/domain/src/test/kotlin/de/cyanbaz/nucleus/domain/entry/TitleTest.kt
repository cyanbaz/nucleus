package de.cyanbaz.nucleus.domain.entry

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TitleTest {
    @Test
    fun `should create title when value is valid`() {
        val title = Title("Nucleus")

        assertThat(title.value).isEqualTo("Nucleus")
    }

    @Test
    fun `should throw exception when title is blank`() {
        assertThatThrownBy { Title("   ") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Title must not be blank")
    }

    @Test
    fun `should throw exception when title is too long`() {
        val tooLongTitle = "a".repeat(201)

        assertThatThrownBy { Title(tooLongTitle) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Title must not be longer than 200 characters")
    }
}
