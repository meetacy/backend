package app.meetacy.backend.di

data class DependencyTrace(
    val list: List<DependencyKey<*>>
)
