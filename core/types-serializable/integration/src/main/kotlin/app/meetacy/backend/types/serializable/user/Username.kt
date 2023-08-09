package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.Username
import app.meetacy.backend.types.serializable.user.Username as UsernameSerializable

fun UsernameSerializable.type(): Username = Username.parse(string)
fun Username.serializable(): UsernameSerializable = UsernameSerializable(string)
