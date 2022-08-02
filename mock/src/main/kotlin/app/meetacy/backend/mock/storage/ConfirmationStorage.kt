package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.UserId

object ConfirmationStorage {
    private val data: MutableList<MockConfirmHash> = mutableListOf()

    fun addHash(ownerId: UserId, email: String, confirmHash: String) {
        data += MockConfirmHash(ownerId, email, confirmHash)
    }

    fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
        data.firstOrNull { it.email == email && it.confirmHash == confirmHash }?.ownerId

    fun deleteHashes(email: String) =
        data.removeIf { it.email == email }
}
