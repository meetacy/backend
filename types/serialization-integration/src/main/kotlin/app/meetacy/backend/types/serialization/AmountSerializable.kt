package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.Amount
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AmountSerializable(private val int: Int) {
    fun type() = Amount(int)
}

fun Amount.serializable() = AmountSerializable(int)
