package hm.binkley.labs

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * @see ChildrenTest
 * @see ListMapTreeTest
 */
internal class ChildrenTest {
    @Test
    fun `should create a child node`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newNode("CHILD")

        child.name shouldBe "CHILD"
    }

    @Test
    fun `should complain when creating a duplicate child node`() {
        val root = ListMapTree.newRoot("ROOT")

        root.newNode("CHILD")

        shouldThrow<IllegalArgumentException> {
            root.newNode("CHILD")
        }
    }

    @Test
    fun `first child node depth should be 1`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newNode("CHILD")

        child.depth shouldBe 1
    }

    @Test
    fun `root should remember child nodes`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newNode("CHILD")

        root.children shouldBe listOf(child)
    }

    @Test
    fun `should sort child nodes by name`() {
        val root = ListMapTree.newRoot("ROOT")
        val childA = root.newNode("A-CHILD")
        val childB = root.newNode("B-CHILD")
        val childD = root.newNode("D-CHILD")
        val childC = root.newNode("C-CHILD")

        root.children shouldBe listOf(childA, childB, childC, childD)
    }
}
