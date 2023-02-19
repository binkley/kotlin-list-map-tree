package hm.binkley.labs

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
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
            setProperty("FOO", IntegerPropertyValue(7))
        }

        root.toString() shouldBe "ListMapTree[name=ROOT, depth=0, nodes=[ListMapTree[name=NODE, depth=1, nodes=[], properties={}]], properties={FOO=IntegerPropertyValue(value=7)}]" // ktlint-disable max-line-length
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

    @Test
    fun `should be convenient to set an empty property`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("FOO")
        val previous = root.setProperty("FOO", null)

        root.properties["FOO"] shouldBe EmptyPropertyValue
        previous shouldBe EmptyPropertyValue
    }

    @Test
    fun `should be convenient to set a binary data property`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousData = "\u1F337".toByteArray() // 🌷
        val data = "\u1F940".toByteArray() // 🥀

        root.setProperty("FOO", previousData)
        val previous = root.setProperty("FOO", data)

        root.properties["FOO"] shouldBe BinaryDataPropertyValue(data)
        previous shouldBe BinaryDataPropertyValue(previousData)
    }

    @Test
    fun `should be convenient to set an integer property with a long`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 13L
        val number = 21L

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number)
        previous shouldBe IntegerPropertyValue(previousNumber)
    }

    @Test
    fun `should be convenient to set an integer property with an int`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 13
        val number = 21

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number.toLong())
        previous shouldBe IntegerPropertyValue(previousNumber.toLong())
    }

    @Test
    fun `should be convenient to set an integer property with a short`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 13.toShort()
        val number = 21.toShort()

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number.toLong())
        previous shouldBe IntegerPropertyValue(previousNumber.toLong())
    }

    @Test
    fun `should be convenient to set an integer property with a byte`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 0b1101.toByte() // 13
        val number = 0b10101.toByte() // 21

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number.toLong())
        previous shouldBe IntegerPropertyValue(previousNumber.toLong())
    }

    @Test
    fun `should be convenient to set a text property`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousText = "BAR"
        val text = "BAZ"

        root.setProperty("FOO", previousText)
        val previous = root.setProperty("FOO", text)

        root.properties["FOO"] shouldBe TextPropertyValue(text)
        previous shouldBe TextPropertyValue(previousText)
    }

    @Test
    fun `int indices should select nodes`() {
        val root = ListMapTree.newRoot("ROOT")
        val node = root.newNode("NODE")

        root[0] shouldBe node
        shouldThrow<IndexOutOfBoundsException> {
            root[1]
        }
    }

    @Test
    fun `string indices should select properties`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("FOO", "BAR")

        root["FOO"] shouldBe "BAR"
        root["BAZ"] shouldBe null
    }

    @Test
    fun `string (property) indices should be assignable as empty`() {
        val root = ListMapTree.newRoot("ROOT")

        root["FOO"] = null

        root.properties["FOO"] shouldBe EmptyPropertyValue
    }

    @Test
    fun `string (property) indices should be assignable with binary data`() {
        val root = ListMapTree.newRoot("ROOT")
        val data = "\u1F337".toByteArray() // 🌷

        root["FOO"] = data

        root.properties["FOO"] shouldBe BinaryDataPropertyValue(data)
    }

    @Test
    fun `string (property) indices should be assignable with numbers`() {
        val root = ListMapTree.newRoot("ROOT")

        root["FOO"] = 13

        root.properties["FOO"] shouldBe IntegerPropertyValue(13)
    }

    @Test
    fun `string (property) indices should be assignable with text`() {
        val root = ListMapTree.newRoot("ROOT")

        root["FOO"] = "BAR"

        root.properties["FOO"] shouldBe TextPropertyValue("BAR")
    }
}
