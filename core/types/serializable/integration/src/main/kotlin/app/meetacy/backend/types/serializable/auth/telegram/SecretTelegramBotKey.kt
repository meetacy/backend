package app.meetacy.backend.types.serializable.auth.telegram

import app.meetacy.backend.types.serializable.auth.telegram.SecretTelegramBotKey as SecretTelegramBotKeySerializable
import app.meetacy.backend.types.auth.telegram.SecretTelegramBotKey

fun SecretTelegramBotKeySerializable.type() = SecretTelegramBotKey(string)
fun SecretTelegramBotKey.serializable() = SecretTelegramBotKeySerializable(string)
