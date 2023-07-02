package app.meetacy.backend.di

import app.meetacy.backend.di.SingletonInitialize.Eager
import kotlin.reflect.typeOf

inline fun di(
    dependencies: Dependencies = Dependencies.Empty,
    checkDependencies: Boolean = true,
    block: DIBuilder.() -> Unit
): DI {
    return DIBuilder(dependencies).apply(block).build().apply {
        if (checkDependencies) checkDependencies()
    }
}

inline fun di(
    di: DI,
    checkDependencies: Boolean = true,
    block: DIBuilder.() -> Unit
): DI = di(di.dependencies, checkDependencies, block)

class DIBuilder(dependencies: Dependencies) {
    private val dependencies: MutableList<DependencyPair<*>> = dependencies.list.toMutableList()

    fun <T> register(dependency: DependencyPair<T>) {
        if (dependencies.any { (key) -> key == dependency.key }) {
            error("There is already a dependency with the key ${dependency.key}")
        }
        dependencies += dependency
    }

    fun <T> register(key: DependencyKey<T>, provider: DependencyProvider<T>) {
        register(
            dependency = DependencyPair(key, provider)
        )
    }

    inline fun <reified T> constant(
        name: String,
        value: T
    ) {
        factory(name) { value }
    }

    inline fun <reified T> constant(value: T): DIBuilderConstantDelegate<T> {
        return DIBuilderConstantDelegate(
            di = this,
            type = typeOf<T>(),
            value = value
        )
    }

    inline fun <reified T> singleton(
        name: String? = null,
        initialize: SingletonInitialize = SingletonInitialize.Lazy,
        crossinline factory: DI.() -> T
    ) {
        val key = DependencyKey<T>(
            type = typeOf<T>(),
            name = name
        )
        val provider = object : DependencyProvider.Singleton<T> {
            override val initialize = initialize
            override fun createNewInstance(di: DI): T = di.factory()
        }
        register(key, provider)
    }

    inline fun <reified T> eagerSingleton(
        name: String? = null,
        crossinline factory: DI.() -> T
    ) {
        return singleton(name, Eager, factory)
    }

    inline fun <reified T> singleton(
        initialize: SingletonInitialize = SingletonInitialize.Lazy,
        noinline factory: DI.() -> T
    ): DIBuilderSingletonDelegate<T> {
        return DIBuilderSingletonDelegate(
            di = this,
            type = typeOf<T>(),
            initialize = initialize,
            factory = factory
        )
    }

    inline fun <reified T> eagerSingleton(
        noinline factory: DI.() -> T
    ): DIBuilderSingletonDelegate<T> {
        return singleton(Eager, factory)
    }


    inline fun <reified T> factory(
        name: String? = null,
        crossinline factory: DI.() -> T
    ) {
        val key = DependencyKey<T>(
            type = typeOf<T>(),
            name = name
        )
        val provider = object : DependencyProvider.Factory<T> {
            override fun createNewInstance(di: DI): T = di.factory()
        }
        register(key, provider)
    }

    inline fun <reified T> factory(
        noinline factory: DI.() -> T
    ): DIBuilderFactoryDelegate<T> {
        return DIBuilderFactoryDelegate(
            di = this,
            type = typeOf<T>(),
            factory = factory
        )
    }

    fun build(): DI = DI(
        dependencies = Dependencies(dependencies.toList())
    )
}
