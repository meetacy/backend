package app.meetacy.backend.di.dependency

import app.meetacy.backend.di.DI
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

object Dependency {
    inline operator fun <reified T> getValue(
        thisRef: DI,
        property: KProperty<*>
    ): T = thisRef.get(typeOf<T>(), property.name)
}

class InnerDependency(val di: DI) {
    inline operator fun <reified T> getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T = di.get(typeOf<T>(), property.name)
}
