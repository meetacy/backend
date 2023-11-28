package app.meetacy.backend.types.serializable.auth.telegram

import app.meetacy.backend.types.serializable.auth.telegram.TemporaryTelegramHash as TemporaryTelegramHashSerializable
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.serializable.serialization

fun TemporaryTelegramHashSerializable.type() = serialization { TemporaryTelegramHash(string) }
fun TemporaryTelegramHash.serializable() = TemporaryTelegramHashSerializable(string)
