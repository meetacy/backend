@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.email

import app.meetacy.backend.types.EMAIL_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class ConfirmationTable(private val db: Database) : Table() {
    private val OWNER_ID = long("OWNER_ID")
    private val EMAIL = varchar("EMAIL", length = EMAIL_MAX_LIMIT)
    private val CONFIRM_HASH = varchar("CONFIRM_HASH", length = HASH_LENGTH)

    init {
        transaction(db) {
            SchemaUtils.create(this@ConfirmationTable)
        }
    }

    suspend fun addHash(ownerId: UserId, email: String, confirmHash: String) {
        newSuspendedTransaction(db = db) {
            insert { statement ->
                statement[OWNER_ID] = ownerId.long
                statement[EMAIL] = email
                statement[CONFIRM_HASH] = confirmHash
            }
        }
    }

    suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? = newSuspendedTransaction(db = db) {
        val result = select { (EMAIL eq email) and (CONFIRM_HASH eq confirmHash) }
            .firstOrNull() ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction UserId(result[OWNER_ID])
    }

    suspend fun deleteHashes(email: String) {
        newSuspendedTransaction(db = db) {
            deleteWhere { EMAIL eq email }
        }
    }
}
