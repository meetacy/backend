package app.meetacy.backend.di

import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class GettingDelegate(
    private val di: DI
) {
    private var value: Any? = NULL

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T> getValue(
        type: KType,
        name: String
    ): T {
        if (value !== NULL) return value as T

        return synchronized(lock = this) {
            if (value !== NULL) return value as T
            di.get<T>(type, name).also { initialized -> value = initialized }
        }
    }

    inline operator fun <reified T> getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T = getValue(typeOf<T>(), property.name)

    companion object {
        private val NULL = Any()
    }
}
