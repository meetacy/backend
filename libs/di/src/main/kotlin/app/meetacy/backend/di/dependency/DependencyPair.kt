package app.meetacy.backend.di.dependency

data class DependencyPair<T>(
    val key: DependencyKey<T>,
    val provider: DependencyProvider<T>
)
