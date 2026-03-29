package de.cyanbaz.nucleus

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NucleusApplicationTests {
    @Test
    fun contextLoads() {
        assertThat("").isEqualTo("")
    }
}
