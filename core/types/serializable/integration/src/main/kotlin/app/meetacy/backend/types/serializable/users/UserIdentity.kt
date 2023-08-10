package app.meetacy.backend.types.serializable.users

import app.meetacy.backend.types.users.UserIdentity
import app.meetacy.backend.types.serializable.users.UserIdentity as UserIdentitySerializable

fun UserIdentitySerializable.type(): UserIdentity = UserIdentity.parse(identity)!!
fun UserIdentity.serializable(): UserIdentitySerializable = UserIdentitySerializable(string)
