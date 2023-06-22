package app.meetacy.backend.database.types

import app.meetacy.backend.types.update.UpdateId

data class DatabaseUpdate(
    val id: UpdateId,
    val type: Type,
    val underlyingId: Long
) {
    enum class Type {
        Notification
    }
}
