package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.serialization
import io.ktor.http.*
import kotlinx.serialization.SerializationException

fun Parameters.amountOrNull(name: String = "amount") = serialization {
    val amount = this[name]
    amount?.toInt()?.let(::Amount)
}
fun Parameters.amount(name: String = "amount") = serialization {
    val amount = this[name] ?: throw SerializationException("Bad request. Illegal input: param 'amount' is required for type with serial name, but it was missing at path: $name")
    Amount(amount.toInt())
}
