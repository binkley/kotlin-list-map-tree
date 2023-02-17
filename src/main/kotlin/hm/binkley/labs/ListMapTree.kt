package hm.binkley.labs

import java.util.Objects.hash
import java.util.TreeMap
import java.util.TreeSet

class ListMapTree private constructor(val name: String, val depth: Int) :
    Comparable<ListMapTree> {
    private val _nodes = TreeSet<ListMapTree>()
    val nodes get() = _nodes.toList()

    fun newNode(name: String) = ListMapTree(name, depth + 1).also {
        if (_nodes.map { it.name }.contains(name)) {
            throw IllegalArgumentException("Duplicate name: $name")
        }
        _nodes.add(it)
    }

    private val _properties = TreeMap<String, PropertyValue<*>>()
    val properties: Map<String, PropertyValue<*>> = _properties

    fun setProperty(name: String, value: PropertyValue<*>) =
        _properties.put(name, value)

    override fun compareTo(other: ListMapTree) = name.compareTo(other.name)

    override fun equals(other: Any?): Boolean = this === other ||
        other is ListMapTree &&
        name == other.name &&
        depth == other.depth &&
        _nodes == other._nodes &&
        _properties == other._properties

    override fun hashCode() = hash(name, depth, _nodes, _properties)

    override fun toString() =
        "ListMapTree[name=$name, depth=$depth, nodes=$_nodes, properties=$_properties]" // ktlint-disable max-line-length

    companion object {
        fun newRoot(name: String) = ListMapTree(name, 0)
    }
}

fun ListMapTree.setProperty(name: String) =
    setProperty(name, EmptyPropertyValue)

fun ListMapTree.setProperty(name: String, data: ByteArray) =
    setProperty(name, BinaryDataPropertyValue(data))

fun ListMapTree.setProperty(name: String, number: Long) =
    setProperty(name, IntegerPropertyValue(number))

fun ListMapTree.setProperty(name: String, number: Int) =
    setProperty(name, IntegerPropertyValue(number.toLong()))

fun ListMapTree.setProperty(name: String, number: Short) =
    setProperty(name, IntegerPropertyValue(number.toLong()))

fun ListMapTree.setProperty(name: String, number: Byte) =
    setProperty(name, IntegerPropertyValue(number.toLong()))

fun ListMapTree.setProperty(name: String, text: String) =
    setProperty(name, TextPropertyValue(text))
