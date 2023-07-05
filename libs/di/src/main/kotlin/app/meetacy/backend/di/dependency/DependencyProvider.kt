package app.meetacy.backend.di.dependency

import app.meetacy.backend.di.DI

sealed interface DependencyProvider<out T> {

    fun createNewInstance(di: DI): T

    interface Factory<T> : DependencyProvider<T>

    interface Singleton<T> : DependencyProvider<T> {
        val initialize: SingletonInitialize
    }
}
