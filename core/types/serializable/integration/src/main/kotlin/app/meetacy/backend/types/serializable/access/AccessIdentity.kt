package app.meetacy.backend.types.serializable.access

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

fun AccessIdentitySerializable.type() = AccessIdentity.parse(string)!!
fun AccessIdentity.serializable() = AccessIdentitySerializable(string)
