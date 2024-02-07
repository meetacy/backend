package app.meetacy.backend.types.serializable.description

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Description(val string: String)

fun Description.serializable() = Description(string)
