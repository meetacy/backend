package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.serializable.users.Username as UsernameSerializable

fun UsernameSerializable.type(): Username = Username.parse(string)
fun Username.serializable(): UsernameSerializable = UsernameSerializable(string)
