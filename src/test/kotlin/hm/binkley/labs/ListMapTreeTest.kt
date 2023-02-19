package hm.binkley.labs

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

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
            newNode("CHILD")
            setProperty("FOO", IntegerPropertyValue(7))
        }

        root.toString() shouldBe "ListMapTree[name=ROOT, depth=0, children=[ListMapTree[name=CHILD, depth=1, children=[], properties={}]], properties={FOO=IntegerPropertyValue(value=7)}]" // ktlint-disable max-line-length
    }

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
    fun `should set an empty property`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("FOO", Empty)
        val previous = root.setProperty("FOO", Empty)

        root.properties["FOO"] shouldBe EmptyPropertyValue
        previous shouldBe EmptyPropertyValue
    }

    @Test
    fun `should set a binary data property`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousData = "\u1F337".toByteArray() // 🌷
        val data = "\u1F940".toByteArray() // 🥀

        root.setProperty("FOO", previousData)
        val previous = root.setProperty("FOO", data)

        root.properties["FOO"] shouldBe BinaryDataPropertyValue(data)
        previous shouldBe BinaryDataPropertyValue(previousData)
    }

    @Test
    fun `should set an integer property with a long`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 13L
        val number = 21L

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number)
        previous shouldBe IntegerPropertyValue(previousNumber)
    }

    @Test
    fun `should set an integer property with an int`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 13
        val number = 21

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number.toLong())
        previous shouldBe IntegerPropertyValue(previousNumber.toLong())
    }

    @Test
    fun `should set an integer property with a short`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 13.toShort()
        val number = 21.toShort()

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number.toLong())
        previous shouldBe IntegerPropertyValue(previousNumber.toLong())
    }

    @Test
    fun `should set an integer property with a byte`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousNumber = 0b1101.toByte() // 13
        val number = 0b10101.toByte() // 21

        root.setProperty("FOO", previousNumber)
        val previous = root.setProperty("FOO", number)

        root.properties["FOO"] shouldBe IntegerPropertyValue(number.toLong())
        previous shouldBe IntegerPropertyValue(previousNumber.toLong())
    }

    @Test
    fun `should set a text property`() {
        val root = ListMapTree.newRoot("ROOT")
        val previousText = "BAR"
        val text = "BAZ"

        root.setProperty("FOO", previousText)
        val previous = root.setProperty("FOO", text)

        root.properties["FOO"] shouldBe TextPropertyValue(text)
        previous shouldBe TextPropertyValue(previousText)
    }

    @Test
    fun `should remove a property`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("FOO", "BAR")
        val previous = root.removeProperty("FOO")

        root.properties.keys.contains("FOO") shouldBe false
        previous shouldBe TextPropertyValue("BAR")
    }

    @Test
    fun `should select a child node with an index`() {
        val root = ListMapTree.newRoot("ROOT")
        val child = root.newNode("CHILD")

        root[0] shouldBe child
        shouldThrow<IndexOutOfBoundsException> {
            root[1]
        }
    }

    @Test
    fun `should select a property with an index`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("FOO", "BAR")

        root["FOO"] shouldBe "BAR"
        root["BAZ"] shouldBe null
    }

    @Test
    fun `should remove a property with an index`() {
        val root = ListMapTree.newRoot("ROOT")

        root.setProperty("FOO", "BAR")
        root["FOO"] = null

        root.properties.keys.contains("FOO") shouldBe false
    }

    @Test
    fun `should assign an empty property`() {
        val root = ListMapTree.newRoot("ROOT")

        root["FOO"] = Empty

        root.properties["FOO"] shouldBe EmptyPropertyValue
    }

    @Test
    fun `should assign a property with binary data`() {
        val root = ListMapTree.newRoot("ROOT")
        val data = "\u1F337".toByteArray() // 🌷

        root["FOO"] = data

        root.properties["FOO"] shouldBe BinaryDataPropertyValue(data)
    }

    @Test
    fun `should assign a property with a number`() {
        val root = ListMapTree.newRoot("ROOT")

        root["FOO"] = 13

        root.properties["FOO"] shouldBe IntegerPropertyValue(13)
    }

    @Test
    fun `should assign a property with text`() {
        val root = ListMapTree.newRoot("ROOT")

        root["FOO"] = "BAR"

        root.properties["FOO"] shouldBe TextPropertyValue("BAR")
    }
}
