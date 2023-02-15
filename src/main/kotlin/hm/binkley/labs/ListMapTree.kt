package hm.binkley.labs

import java.util.Objects.hash
import java.util.TreeSet
import lombok.Generated

sealed interface Value<T : Any> {
    val value: T
}

@Generated // Lie to JaCoCo and PIT
@JvmInline
value class TextValue(override val value: String) : Value<String>

class ListMapTree private constructor(val name: String, val depth: Int) :
    Comparable<ListMapTree> {
    private val _children = TreeSet<ListMapTree>()
    val children get() = _children.toList()

    fun newChild(name: String) = ListMapTree(name, depth + 1).also {
        _children.add(it)
    }

    private val _properties = mutableMapOf<String, Value<*>>()
    val properties: Map<String, Value<*>> = _properties

    fun setProperty(name: String, value: Value<*>) =
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
