package app.meetacy.backend.types.serializable.amount

import  app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.serializable.amount.Amount as AmountSerializable

fun AmountSerializable.type() = Amount.parse(int)
fun Amount.serializable(): AmountSerializable = AmountSerializable(int)
