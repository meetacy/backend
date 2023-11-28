package app.meetacy.backend.types.serializable.access

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

fun AccessIdentitySerializable.type() = serialization { AccessIdentity.parse(string)!! }
fun AccessIdentity.serializable() = AccessIdentitySerializable(string)
