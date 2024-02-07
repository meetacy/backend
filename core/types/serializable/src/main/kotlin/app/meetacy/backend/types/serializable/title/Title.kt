package app.meetacy.backend.types.serializable.title

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Title(val string: String)

fun Title.serializable() = Title(string)
