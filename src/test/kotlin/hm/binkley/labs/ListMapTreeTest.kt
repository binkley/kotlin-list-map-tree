package hm.binkley.labs

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ListMapTreeTest {
    @Test
    fun `should make a tree`() {
        val tree = ListMapTree("ROOT")

        tree.name shouldBe "ROOT"
    }
}
