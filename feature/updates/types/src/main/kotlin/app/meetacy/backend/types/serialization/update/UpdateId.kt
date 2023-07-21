package app.meetacy.backend.types.serialization.update

import app.meetacy.backend.types.update.UpdateId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UpdateIdSerializable(val string: String) {
    fun type() = UpdateId(string.toLong())
}

fun UpdateId.serializable(): UpdateIdSerializable = UpdateIdSerializable(long.toString())
