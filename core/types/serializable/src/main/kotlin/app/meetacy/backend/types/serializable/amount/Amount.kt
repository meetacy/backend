package app.meetacy.backend.types.serializable.amount

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Amount(val int: Int)

fun Amount.serializable() = Amount(int)
