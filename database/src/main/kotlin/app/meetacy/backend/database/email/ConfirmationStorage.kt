@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.email

import app.meetacy.backend.database.email.ConfirmationTable.CONFIRM_HASH
import app.meetacy.backend.database.email.ConfirmationTable.EMAIL
import app.meetacy.backend.database.email.ConfirmationTable.OWNER_ID
import app.meetacy.backend.types.EMAIL_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ConfirmationTable : Table() {
    val OWNER_ID = long("OWNER_ID")
    val EMAIL = varchar("EMAIL", EMAIL_MAX_LIMIT)
    val CONFIRM_HASH = varchar("CONFIRM_HASH", HASH_LENGTH)
}

class ConfirmationStorage(private val db: Database) {

    suspend fun addHash(ownerId: UserId, email: String, confirmHash: String) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            ConfirmationTable.insert { statement ->
                statement[OWNER_ID] = ownerId.long
                statement[EMAIL] = email
                statement[CONFIRM_HASH] = confirmHash
            }
        }
    }

    suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? = newSuspendedTransaction(Dispatchers.IO, db) {
        val result = ConfirmationTable.select { (EMAIL eq email) and (CONFIRM_HASH eq confirmHash) }
            .firstOrNull() ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction UserId(result[OWNER_ID])
    }

    suspend fun deleteHashes(email: String) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            ConfirmationTable.deleteWhere { EMAIL eq email }
        }
    }
}
