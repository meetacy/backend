package app.meetacy.backend.types.serializable.file

import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.serializable.file.FileIdentity as FileIdentitySerializable

fun FileIdentitySerializable.type() = FileIdentity.parse(string)!!
fun FileIdentity.serializable() = FileIdentitySerializable(string)
