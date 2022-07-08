package app.meetacy.backend.mock.storage

object ConfirmationStorage {
    private val data: MutableList<MockConfirmHash> = mutableListOf()

    fun addHash(userId: Int, email: String, confirmHash: String) {
        data += MockConfirmHash(userId, email, confirmHash)
    }

    fun isValidConfirmHash(userId: Int, email: String, confirmHash: String): Boolean =
        data.contains(MockConfirmHash(userId, email, confirmHash))

    fun deleteHash(userId: Int, email: String, confirmHash: String) =
        data.remove(MockConfirmHash(userId, email, confirmHash))
}
