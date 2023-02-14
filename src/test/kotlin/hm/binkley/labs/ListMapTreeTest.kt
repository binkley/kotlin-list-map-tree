package hm.binkley.labs

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
}
