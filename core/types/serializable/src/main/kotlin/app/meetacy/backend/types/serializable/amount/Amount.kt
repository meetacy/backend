package app.meetacy.backend.types.serializable.amount

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Amount(val int: Int) {
    @Serializable
    @JvmInline
    value class OrZero(val int: Int)
}
