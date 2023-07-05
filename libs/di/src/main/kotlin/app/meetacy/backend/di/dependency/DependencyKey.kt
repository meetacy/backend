package app.meetacy.backend.di.dependency

import kotlin.reflect.KType

data class DependencyKey<T>(
    val type: KType,
    val name: String? = null
)
