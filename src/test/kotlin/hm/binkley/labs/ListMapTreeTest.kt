package hm.binkley.labs

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class ListMapTreeTest {
    @Test
    fun `should make a root`() {
        val root = ListMapTree.newRoot("ROOT")

        root.name shouldBe "ROOT"
    }

    @Test
    fun `root depth should be 0`() {
        val root = ListMapTree.newRoot("ROOT")

        root.depth shouldBe 0
    }

    @Test
    fun `trees should display nicely for debugging`() {
        val root = ListMapTree.newRoot("ROOT").apply {
            newChild("CHILD")
        }

        root.toString() shouldBe "ListMapTree[name=ROOT, depth=0, children=[ListMapTree[name=CHILD, depth=1, children=[]]]]" // ktlint-disable max-line-length
    }

    @Test
    fun `equality should cope with pathological cases`() {
        val root = ListMapTree.newRoot("ROOT")

        root.equals(null) shouldBe false
        root.equals(this) shouldBe false
    }

    @Test
    fun `equality and hashing should consider name`() {
        val rootA = ListMapTree.newRoot("A-ROOT")
        val rootB = ListMapTree.newRoot("B-ROOT")

        rootA.equals(rootB) shouldBe false
        rootA.hashCode() shouldNotBe rootB.hashCode()

        val rootC = ListMapTree.newRoot("ROOT")
        val rootD = ListMapTree.newRoot("ROOT")

        rootC.equals(rootD) shouldBe true
        rootC.hashCode() shouldBe rootD.hashCode()
    }

    @Test
    fun `equality and hashing should consider depth`() {
        val rootA = ListMapTree.newRoot("ROOT")
        val child = rootA.newChild("ROOT")

        child.newChild("ROOT")

        rootA.equals(child) shouldBe false
        rootA.hashCode() shouldNotBe child.hashCode()
    }

    @Test
    fun `equality and hashing should consider children`() {
        val rootA = ListMapTree.newRoot("ROOT")
        val rootB = ListMapTree.newRoot("ROOT")

        rootA.newChild("CHILD")

        rootA.equals(rootB) shouldBe false
        rootA.hashCode() shouldNotBe rootB.hashCode()

        val rootC = ListMapTree.newRoot("ROOT")
        val rootD = ListMapTree.newRoot("ROOT")

        rootC.newChild("CHILD")
        rootD.newChild("CHILD")

        rootC.equals(rootD) shouldBe true
        rootC.hashCode() shouldBe rootD.hashCode()
    }

    @Test
    fun `should create a child`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newChild("CHILD")

        child.name shouldBe "CHILD"
    }

    @Test
    fun `should complain about creating a duplicate child`() {
        val root = ListMapTree.newRoot("ROOT")

        root.newChild("CHILD")

        shouldThrow<IllegalArgumentException> {
            root.newChild("CHILD")
        }
    }

    @Test
    fun `first child depth should be 1`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newChild("CHILD")

        child.depth shouldBe 1
    }

    @Test
    fun `root should remember children`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newChild("CHILD")

        root.children shouldBe listOf(child)
    }

    @Test
    fun `should sort children by name`() {
        val root = ListMapTree.newRoot("ROOT")
        val childA = root.newChild("A-CHILD")
        val childB = root.newChild("B-CHILD")
        val childD = root.newChild("D-CHILD")
        val childC = root.newChild("C-CHILD")

        root.children shouldBe listOf(childA, childB, childC, childD)
    }

    @Test
    fun `should start with no properties`() {
        val root = ListMapTree.newRoot("ROOT")

        root.properties shouldBe emptyMap()
    }

    @Test
    fun `should have properties`() {
        val root = ListMapTree.newRoot("ROOT")

        val previous = root.setProperty("FOO", TextPropertyValue("BAR"))

        previous shouldBe null
        root.properties shouldBe mapOf("FOO" to TextPropertyValue("BAR"))

        val nextPrevious = root.setProperty("FOO", TextPropertyValue("BAZ"))

        nextPrevious shouldBe TextPropertyValue("BAR")
        root.properties shouldBe mapOf("FOO" to TextPropertyValue("BAZ"))
    }

    @Test
    fun `should sort properties by name`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("A", TextPropertyValue("BAR"))
        root.setProperty("B", TextPropertyValue("BAR"))
        root.setProperty("D", TextPropertyValue("BAR"))
        root.setProperty("C", TextPropertyValue("BAR"))

        val keys = root.properties.keys.toList()

        keys shouldBe listOf("A", "B", "C", "D")
    }
}
