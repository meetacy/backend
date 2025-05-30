package app.meetacy.backend.types.serializable.amount

import  app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.amount.Amount as AmountSerializable

fun AmountSerializable.type() = serialization { Amount.parse(int) }
fun Amount.serializable(): AmountSerializable = AmountSerializable(int)

fun AmountSerializable.OrZero.type() = serialization { Amount.OrZero.parse(int) }
fun Amount.OrZero.serializable() = AmountSerializable.OrZero(int)
