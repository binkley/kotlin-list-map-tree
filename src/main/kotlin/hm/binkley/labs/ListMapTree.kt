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
        _nodes == other._nodes

    override fun hashCode() = hash(name, depth, _nodes)

    override fun toString() =
        "ListMapTree[name=$name, depth=$depth, nodes=$_nodes]"

    companion object {
        fun newRoot(name: String) = ListMapTree(name, 0)
    }
}
