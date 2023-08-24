package hm.binkley.labs

import java.util.TreeMap
import java.util.TreeSet

class ListMapTree private constructor(val name: String, val depth: Int) :
    Comparable<ListMapTree> {
    private val _children = TreeSet<ListMapTree>()
    val children get() = _children.toList()

    /** @throws IllegalArgumentException if another child already has `name` */
    fun addChild(name: String) = ListMapTree(name, depth + 1).also {
        if (_children.map { child -> child.name }.contains(name)) {
            throw IllegalArgumentException("Duplicate child name: $name")
        }
        _children.add(it)
    }

    fun removeChild(name: String): Boolean {
        // TODO: Nicer way to express this
        val found = _children.find { it.name == name } ?: return false
        _children.remove(found)
        return true
    }

    private val _properties = TreeMap<String, PropertyValue<*>>()
    val properties: Map<String, PropertyValue<*>> = _properties

    fun setProperty(name: String, value: PropertyValue<*>) =
        _properties.put(name, value)

    fun removeProperty(name: String) = _properties.remove(name)

    override fun compareTo(other: ListMapTree) = name.compareTo(other.name)

    @Suppress("ktlint:standard:max-line-length")
    override fun toString() =
        "ListMapTree[name=$name, depth=$depth, children=$_children, properties=$_properties]"

    companion object {
        fun newRoot(name: String) = ListMapTree(name, 0)
    }
}

fun ListMapTree.setProperty(
    name: String,
    @Suppress("UNUSED_PARAMETER") empty: Empty
) =
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

operator fun ListMapTree.get(index: Int) = children[index]

operator fun ListMapTree.get(key: String) = properties[key]?.value

operator fun ListMapTree.set(
    key: String,
    @Suppress("UNUSED_PARAMETER") missing: Unit?
) {
    removeProperty(key)
}

operator fun ListMapTree.set(key: String, empty: Empty) {
    setProperty(key, empty)
}

operator fun ListMapTree.set(key: String, data: ByteArray) {
    setProperty(key, data)
}

operator fun ListMapTree.set(key: String, number: Long) {
    setProperty(key, number)
}

operator fun ListMapTree.set(key: String, text: String) {
    setProperty(key, text)
}
