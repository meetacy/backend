package app.meetacy.backend.di

data class DependencyPair<T>(
    val key: DependencyKey<T>,
    val provider: DependencyProvider<T>
)
