package hm.binkley.labs

import java.util.Objects.hash
import java.util.TreeMap
import java.util.TreeSet

class ListMapTree private constructor(val name: String, val depth: Int) :
    Comparable<ListMapTree> {
    private val _children = TreeSet<ListMapTree>()
    val children get() = _children.toList()

    fun newChild(name: String) = ListMapTree(name, depth + 1).also {
        if (_children.map { it.name }.contains(name)) {
            throw IllegalArgumentException("Duplicate name: $name")
        }
        _children.add(it)
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
        _children == other._children

    override fun hashCode() = hash(name, depth, _children)

    override fun toString() =
        "ListMapTree[name=$name, depth=$depth, children=$_children]"

    companion object {
        fun newRoot(name: String) = ListMapTree(name, 0)
    }
}
