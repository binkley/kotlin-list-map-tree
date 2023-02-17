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
            newNode("NODE")
            setProperty("FOO", IntPropertyValue(7))
        }

        root.toString() shouldBe "ListMapTree[name=ROOT, depth=0, nodes=[ListMapTree[name=NODE, depth=1, nodes=[], properties={}]], properties={FOO=IntPropertyValue(value=7)}]" // ktlint-disable max-line-length
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
        val node = rootA.newNode("ROOT")

        node.newNode("ROOT")

        rootA.equals(node) shouldBe false
        rootA.hashCode() shouldNotBe node.hashCode()
    }

    @Test
    fun `equality and hashing should consider nodes`() {
        val rootA = ListMapTree.newRoot("ROOT")
        val rootB = ListMapTree.newRoot("ROOT")

        rootA.newNode("NODE")

        rootA.equals(rootB) shouldBe false
        rootA.hashCode() shouldNotBe rootB.hashCode()

        val rootC = ListMapTree.newRoot("ROOT")
        val rootD = ListMapTree.newRoot("ROOT")

        rootC.newNode("NODE")
        rootD.newNode("NODE")

        rootC.equals(rootD) shouldBe true
        rootC.hashCode() shouldBe rootD.hashCode()
    }

    @Test
    fun `equality and hashing should consider properties`() {
        val rootA = ListMapTree.newRoot("ROOT")
        val rootB = ListMapTree.newRoot("ROOT")

        rootA.setProperty("FOO", EmptyPropertyValue)

        rootA.equals(rootB) shouldBe false
        rootA.hashCode() shouldNotBe rootB.hashCode()

        val rootC = ListMapTree.newRoot("ROOT")
        val rootD = ListMapTree.newRoot("ROOT")

        rootC.setProperty("FOO", EmptyPropertyValue)
        rootD.setProperty("FOO", EmptyPropertyValue)

        rootC.equals(rootD) shouldBe true
        rootC.hashCode() shouldBe rootD.hashCode()
    }

    @Test
    fun `should create a node`() {
        val root = ListMapTree.newRoot("ROOT")
        val node = root.newNode("NODE")

        node.name shouldBe "NODE"
    }

    @Test
    fun `should complain about creating a duplicate node`() {
        val root = ListMapTree.newRoot("ROOT")

        root.newNode("NODE")

        shouldThrow<IllegalArgumentException> {
            root.newNode("NODE")
        }
    }

    @Test
    fun `first node depth should be 1`() {
        val root = ListMapTree.newRoot("ROOT")
        val node = root.newNode("NODE")

        node.depth shouldBe 1
    }

    @Test
    fun `root should remember nodes`() {
        val root = ListMapTree.newRoot("ROOT")
        val node = root.newNode("NODE")

        root.nodes shouldBe listOf(node)
    }

    @Test
    fun `should sort nodes by name`() {
        val root = ListMapTree.newRoot("ROOT")
        val nodeA = root.newNode("A-NODE")
        val nodeB = root.newNode("B-NODE")
        val nodeD = root.newNode("D-NODE")
        val nodeC = root.newNode("C-NODE")

        root.nodes shouldBe listOf(nodeA, nodeB, nodeC, nodeD)
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

        root.setProperty("A", EmptyPropertyValue)
        root.setProperty("B", EmptyPropertyValue)
        root.setProperty("D", EmptyPropertyValue)
        root.setProperty("C", EmptyPropertyValue)

        val keys = root.properties.keys.toList()

        keys shouldBe listOf("A", "B", "C", "D")
    }
}
