package app.meetacy.backend.di.builder

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.dependency.SingletonInitialize
import app.meetacy.backend.di.dependency.DependencyKey
import app.meetacy.backend.di.dependency.DependencyProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType

class DIBuilderConstantDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val value: T
) {
    operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(
                type = type,
                name = property.name
            ),
            provider = object : DependencyProvider.Factory<T> {
                override fun createNewInstance(di: DI): T = value
            }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}

class DIBuilderSingletonDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val initialize: SingletonInitialize,
    private val factory: DI.() -> T
) {
    operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(
                type = type,
                name = property.name
            ),
            provider = object : DependencyProvider.Singleton<T> {
                override val initialize = this@DIBuilderSingletonDelegate.initialize
                override fun createNewInstance(di: DI): T = di.factory()
            }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}

class DIBuilderFactoryDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val factory: DI.() -> T
) {
    operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(
                type = type,
                name = property.name
            ),
            provider = object : DependencyProvider.Factory<T> {
                override fun createNewInstance(di: DI): T = di.factory()
            }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}
