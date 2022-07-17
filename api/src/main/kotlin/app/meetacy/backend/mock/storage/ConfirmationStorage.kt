package app.meetacy.backend.mock.storage

object ConfirmationStorage {
    private val data: MutableList<MockConfirmHash> = mutableListOf()

    fun addHash(ownerId: Long, email: String, confirmHash: String) {
        data += MockConfirmHash(ownerId, email, confirmHash)
    }

    fun getConfirmHashOwnerId(email: String, confirmHash: String): Long? =
        data.firstOrNull { it.email == email && it.confirmHash == confirmHash }?.ownerId

    fun deleteHashes(email: String) =
        data.removeIf { it.email == email }
}
