package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.UserId

fun UserIdSerializable.type() = UserId(long)

fun UserId.serializable() = UserIdSerializable(long)
