package app.meetacy.backend.types.serializable.prelogin

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class TemporaryTelegramToken(val string: String)
