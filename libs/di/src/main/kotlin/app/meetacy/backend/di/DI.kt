package app.meetacy.backend.di

import app.meetacy.backend.di.annotation.DIDsl
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.builder.di
import app.meetacy.backend.di.dependency.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@DIDsl
class DI private constructor(
    val dependencies: Dependencies,
    private val trace: DependencyTrace,
    private val singletons: MutableMap<DependencyKey<*>, Any?>
) {
    private val dependenciesList = dependencies.list

    constructor(dependencies: Dependencies) : this(
        dependencies = dependencies,
        trace = DependencyTrace(emptyList()),
        singletons = mutableMapOf()
    ) {
        for (dependency in dependenciesList) {
            val provider = dependency.provider

            if (provider !is DependencyProvider.Singleton) continue
            if (provider.initialize != SingletonInitialize.Eager) continue

            provideSingleton(dependency)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> providerOrNull(key: DependencyKey<T>): DependencyProvider<T>? {
        val dependency = dependenciesList
            .firstOrNull { (currentKey) -> currentKey == key }
            ?: dependenciesList.singleOrNull { (currentKey) -> currentKey.type == key.type }

        return dependency?.provider as DependencyProvider<T>?
    }

    fun <T> provider(key: DependencyKey<T>): DependencyProvider<T> =
        providerOrNull(key) ?: error("Dependency $key could not be resolved")

    fun <T> get(
        key: DependencyKey<T>
    ): T {
        val provider = provider(key)
        val dependency = DependencyPair(key, provider)
        return provide(dependency)
    }

    private fun <T> provide(
        dependency: DependencyPair<T>
    ): T {
        checkRecursive(dependency)
        return when (dependency.provider) {
            is DependencyProvider.Factory -> dependency.provider.createNewInstance(subDI(dependency.key))
            is DependencyProvider.Singleton -> provideSingleton(dependency)
        }
    }

    private fun checkRecursive(dependency: DependencyPair<*>) {
        if (trace.list.any { key -> key == dependency.key }) {
            val dependencies = trace.list.joinToString(separator = " -> ") { "$it" }
            error("Recursive cycle found, cannot resolve the dependency. Cycle: $dependencies")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> provideSingleton(
        dependency: DependencyPair<T>
    ): T {
        if (dependency.key in singletons) return singletons.getValue(dependency.key) as T

        return synchronized(lock = singletons) {
            if (dependency.key in singletons) return singletons.getValue(dependency.key) as T
            val instance = dependency.provider.createNewInstance(subDI(dependency.key))
            singletons[dependency.key] = instance
            instance
        }
    }

    fun <T> get(
        type: KType,
        name: String? = null
    ): T {
        val key = DependencyKey<T>(type, name)
        return get(key)
    }

    inline fun <reified T> get(
        name: String? = null
    ): T {
        return get(
            type = typeOf<T>(),
            name = name
        )
    }

    infix fun extend(block: DIBuilder.() -> Unit): DI = this + di(checkDependencies = false, block = block)

    // TODO: add trace and singletons, so that initialization of singleton made in
    //  one of children also saves it for parent
    operator fun plus(other: DI): DI = DI(
        dependencies = Dependencies(list = dependencies.list + other.dependencies.list),
//        trace = DependencyTrace(trace.list + other.trace.list),
//        singletons = singletons + other.singletons
    )

    val getting: InnerDependency = InnerDependency(di = this)

    private fun subDI(key: DependencyKey<*>): DI {
        return DI(
            dependencies = dependencies,
            trace = trace.copy(list = trace.list + key),
            singletons = singletons
        )
    }
}
