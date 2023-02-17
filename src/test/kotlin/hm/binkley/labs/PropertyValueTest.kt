package hm.binkley.labs

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PropertyValueTest {
    @Test
    fun `binary data properties should have values`() {
        val data = "\u1F337".toByteArray() // 🌷
        val value = BinaryDataPropertyValue(data)

        value.value shouldBe data
    }

    @Test
    fun `empty properties should exist`() {
        val value = EmptyPropertyValue

        value.value shouldBe Empty
        Empty.toString() shouldBe "Empty"
    }

    @Test
    fun `integer properties should have values`() {
        val value = IntegerPropertyValue(64L)

        value.value shouldBe 64
    }

    @Test
    fun `text properties should have values`() {
        val value = TextPropertyValue("FOO")

        value.value shouldBe "FOO"
    }
}
