package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.users.UserIdentity
import app.meetacy.backend.types.serializable.users.UserId as UserIdentitySerializable

fun UserIdentitySerializable.type() = serialization { UserIdentity.parse(string) }
fun UserIdentity.serializable() = UserIdentitySerializable(string)
