package hm.binkley.labs

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

/**
 * @see ChildrenTest
 * @see PropertiesTest
 */
internal class ListMapTreeTest {
    @Test
    fun `should make a root node`() {
        val root = ListMapTree.newRoot("ROOT")

        root.name shouldBe "ROOT"
    }

    @Test
    fun `root node depth should be 0`() {
        val root = ListMapTree.newRoot("ROOT")

        root.depth shouldBe 0
    }

    @Test
    fun `trees should display nicely for debugging`() {
        val root = ListMapTree.newRoot("ROOT").apply {
            addChild("CHILD")
            setProperty("FOO", IntegerPropertyValue(7))
        }

        root.toString() shouldBe "ListMapTree[name=ROOT, depth=0, children=[ListMapTree[name=CHILD, depth=1, children=[], properties={}]], properties={FOO=IntegerPropertyValue(value=7)}]" // ktlint-disable max-line-length
    }

    @Test
    fun `should navigate as indices`() {
        val root = ListMapTree.newRoot("ROOT")
        root.setProperty("FOO", "BAR")
        val child = root.addChild("CHILD")
        child.setProperty("FOO", "BAZ")

        root["FOO"] shouldBe "BAR"
        root[0]["FOO"] shouldBe "BAZ"
    }
}
