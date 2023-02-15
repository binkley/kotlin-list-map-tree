package hm.binkley.labs

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PropertyValueTest {
    @Test
    fun `text properties should have values`() {
        val value = TextPropertyValue("FOO")

        value.value shouldBe "FOO"
    }

    @Test
    fun `integer properties should have values`() {
        val value = IntPropertyValue(64L)

        value.value shouldBe 64
    }
}
