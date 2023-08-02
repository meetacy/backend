package app.meetacy.backend.types.serializable.update

import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.serializable.update.UpdateId as UpdateIdSerializable

fun UpdateIdSerializable.type() = UpdateId(string.toLong())

fun UpdateId.serializable(): UpdateIdSerializable = UpdateIdSerializable(long.toString())
