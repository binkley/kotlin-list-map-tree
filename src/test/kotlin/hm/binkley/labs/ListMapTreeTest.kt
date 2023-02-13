package hm.binkley.labs

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ListMapTreeTest {
    @Test
    fun `should make a tree`() {
        val tree = ListMapTree.newRoot("ROOT")

        tree.name shouldBe "ROOT"
    }

    @Test
    fun `root depth should be 0`() {
        val tree = ListMapTree.newRoot("ROOT")

        tree.depth shouldBe 0
    }

    @Test
    fun `should create a child`() {
        val tree = ListMapTree.newRoot("ROOT")
        val child = tree.newChild("CHILD")

        child.name shouldBe "CHILD"
    }

    @Test
    fun `first child depth should be 1`() {
        val tree = ListMapTree.newRoot("ROOT")
        val child = tree.newChild("CHILD")

        child.depth shouldBe 1
    }
}
