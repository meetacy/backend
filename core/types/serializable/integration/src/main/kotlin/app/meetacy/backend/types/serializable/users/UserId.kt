package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.serializable.users.UserId as UserIdSerializable

fun UserIdSerializable.type() = UserId(long)
fun UserId.serializable() = UserId(long)
