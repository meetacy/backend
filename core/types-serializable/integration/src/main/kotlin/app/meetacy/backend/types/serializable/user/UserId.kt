package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.serializable.user.UserId as UserIdSerializable

fun UserIdSerializable.type() = UserId(long)
fun UserId.serializable() = UserId(long)
