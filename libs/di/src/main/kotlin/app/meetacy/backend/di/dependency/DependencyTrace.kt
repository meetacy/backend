package app.meetacy.backend.di.dependency

data class DependencyTrace(
    val list: List<DependencyKey<*>>
)
