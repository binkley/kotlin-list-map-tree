package hm.binkley.labs

import lombok.Generated

sealed interface PropertyValue<T : Any> {
    val value: T
}

@Generated // Lie to JaCoCo and PIT
@JvmInline
value class TextPropertyValue(override val value: String) :
    PropertyValue<String>

@Generated // Lie to JaCoCo and PIT
@JvmInline
value class IntPropertyValue(override val value: Long) :
    PropertyValue<Long>

@Generated // Lie to JaCoCo and PIT
@JvmInline
value class BinaryDataPropertyValue(override val value: ByteArray) :
    PropertyValue<ByteArray>

object Empty {
    override fun toString() = "Empty"
}

object EmptyPropertyValue : PropertyValue<Empty> {
    override val value = Empty
}
