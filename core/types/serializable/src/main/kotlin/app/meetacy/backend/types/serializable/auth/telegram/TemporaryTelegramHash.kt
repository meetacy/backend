package app.meetacy.backend.types.serializable.auth.telegram

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class TemporaryTelegramHash(val string: String)
