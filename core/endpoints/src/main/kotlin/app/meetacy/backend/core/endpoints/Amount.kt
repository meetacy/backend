package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.amount.Amount
import io.ktor.server.application.*
import kotlinx.serialization.SerializationException
import app.meetacy.backend.types.serializable.serialization

fun ApplicationCall.amount(): Amount = serialization {
    val amount = parameters["amount"]
    return if (amount == null) {
        throw SerializationException("Bad request. Illegal input: param 'amount' is required for type with serial name, but it was missing at path: $")
    } else {
        Amount(amount.toInt())
    }
}
