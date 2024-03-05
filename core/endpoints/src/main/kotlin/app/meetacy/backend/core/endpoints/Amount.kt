package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.serialization
import io.ktor.http.*

fun Parameters.amountOrNull(name: String = "amount") = serialization {
    val amount = this[name]
    amount?.toInt()?.let(::Amount)
}
