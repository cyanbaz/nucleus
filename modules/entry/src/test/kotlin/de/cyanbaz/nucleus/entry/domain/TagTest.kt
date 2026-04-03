package de.cyanbaz.nucleus.entry.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TagTest {
    @Test
    fun `should create tag when value is valid`() {
        val tag = Tag("kotlin")

        assertThat(tag.value).isEqualTo("kotlin")
    }

    @Test
    fun `should throw exception when tag is blank`() {
        assertThatThrownBy { Tag("   ") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Tag must not be blank")
    }

    @Test
    fun `should throw exception when tag is longer than 50 characters`() {
        val tooLongTag = "a".repeat(51)

        assertThatThrownBy { Tag(tooLongTag) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Tag must not be longer than 50 characters")
    }

    @Test
    fun `should normalize tag`() {
        val tag = Tag(" Kotlin ")

        val normalized = tag.normalized()

        assertThat(normalized.value).isEqualTo("kotlin")
    }
}
