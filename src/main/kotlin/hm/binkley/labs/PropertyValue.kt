package hm.binkley.labs

import lombok.Generated

sealed interface PropertyValue<T : Any> {
    val value: T
}

@Generated // Lie to JaCoCo and PIT
@JvmInline
value class TextPropertyValue(override val value: String) :
    PropertyValue<String>
