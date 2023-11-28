package app.meetacy.backend.types.serializable.file

import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.file.FileIdentity as FileIdentitySerializable

fun FileIdentitySerializable.type() = serialization { FileIdentity.parse(string)!! }
fun FileIdentity.serializable() = FileIdentitySerializable(string)
