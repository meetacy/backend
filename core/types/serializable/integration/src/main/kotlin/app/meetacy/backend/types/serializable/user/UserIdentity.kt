package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.UserIdentity
import app.meetacy.backend.types.serializable.user.UserIdentity as UserIdentitySerializable

fun UserIdentitySerializable.type(): UserIdentity = UserIdentity.parse(identity)!!
fun UserIdentity.serializable(): UserIdentitySerializable = UserIdentitySerializable(string)
