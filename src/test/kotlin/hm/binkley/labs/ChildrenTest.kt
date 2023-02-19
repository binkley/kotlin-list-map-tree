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
        val child = root.addChild("CHILD")

        child.name shouldBe "CHILD"
    }

    @Test
    fun `should complain when creating a duplicate child node`() {
        val root = ListMapTree.newRoot("ROOT")

        root.addChild("CHILD")

        shouldThrow<IllegalArgumentException> {
            root.addChild("CHILD")
        }
    }

    @Test
    fun `first child node depth should be 1`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.addChild("CHILD")

        child.depth shouldBe 1
    }

    @Test
    fun `root should remember child nodes`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.addChild("CHILD")

        root.children shouldBe listOf(child)
    }

    @Test
    fun `should sort child nodes by name`() {
        val root = ListMapTree.newRoot("ROOT")
        val childA = root.addChild("A-CHILD")
        val childB = root.addChild("B-CHILD")
        val childD = root.addChild("D-CHILD")
        val childC = root.addChild("C-CHILD")

        root.children shouldBe listOf(childA, childB, childC, childD)
    }
}
