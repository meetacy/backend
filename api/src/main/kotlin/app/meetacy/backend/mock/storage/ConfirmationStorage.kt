package app.meetacy.backend.mock.storage

object ConfirmationStorage {
    private val data: MutableList<MockConfirmHash> = mutableListOf()

    fun addHash(email: String, confirmHash: String) {
        data += MockConfirmHash(email, confirmHash)
    }

    fun isValidConfirmHash(email: String, confirmHash: String): Boolean =
        data.contains(MockConfirmHash(email, confirmHash))

    fun deleteHash(email: String, confirmHash: String) =
        data.remove(MockConfirmHash(email, confirmHash))
}
