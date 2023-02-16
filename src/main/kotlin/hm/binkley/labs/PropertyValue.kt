package hm.binkley.labs

import lombok.Generated

/**
 * Value types for [ListMapTree.setProperty].
 */
sealed interface PropertyValue<T : Any> {
    /**
     * The property value. Note that [Empty] is special in that it signifies
     * there is no meaningful value.
     */
    val value: T
}

/**
 * A property value for binary data.
 */
@Generated // Lie to JaCoCo and PIT
@JvmInline
value class BinaryDataPropertyValue(override val value: ByteArray) :
    PropertyValue<ByteArray>

/**
 * A nonce property value for a key where presence of the key is sufficient.
 * There is only one instance of `Empty`.
 *
 * @see EmptyPropertyValue
 */
object Empty {
    override fun toString() = "Empty"
}

/**
 * Mark the presence of a property key where the value is irrelevant.
 * There is only one instance of `EmptyPropertyValue`.
 */
object EmptyPropertyValue : PropertyValue<Empty> {
    override val value = Empty
}

/**
 * An integer property value.
 * This is a signed 64-bit integral type.
 */
@Generated // Lie to JaCoCo and PIT
@JvmInline
value class IntPropertyValue(override val value: Long) :
    PropertyValue<Long>

/**
 * A text (string) property value.
 */
@Generated // Lie to JaCoCo and PIT
@JvmInline
value class TextPropertyValue(override val value: String) :
    PropertyValue<String>
