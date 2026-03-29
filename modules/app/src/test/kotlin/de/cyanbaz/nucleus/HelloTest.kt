package de.cyanbaz.nucleus

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HelloTest {
    @Test
    fun `should return hello world`() {
        assertThat(
            "Hello World",
        ).isEqualTo("Hello World")
    }
}
