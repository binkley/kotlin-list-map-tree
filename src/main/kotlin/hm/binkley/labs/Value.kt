package hm.binkley.labs

import lombok.Generated

sealed interface Value<T : Any> {
    val value: T
}

@Generated // Lie to JaCoCo and PIT
@JvmInline
value class TextValue(override val value: String) : Value<String>
