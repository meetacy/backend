@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database

import app.meetacy.backend.types.UserId
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ConfirmationTable(private val db: Database) : Table() {
    private val OWNER_ID = long("OWNER_ID")
    private val EMAIL = varchar("EMAIL", length = 320)
    private val CONFIRM_HASH = varchar("CONFIRM_HASH", length = 256)

    init {
        transaction(db) {
            SchemaUtils.create(this@ConfirmationTable)
        }
    }

    fun addHash(ownerId: UserId, email: String, confirmHash: String) {
        transaction(db) {
            insert { statement ->
                statement[OWNER_ID] = ownerId.long
                statement[EMAIL] = email
                statement[CONFIRM_HASH] = confirmHash
            }
        }
    }

    fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? = transaction(db) {
        val result = select { (EMAIL eq email) and (CONFIRM_HASH eq confirmHash) }
            .firstOrNull() ?: return@transaction null
        return@transaction UserId(result[OWNER_ID])
    }

    fun deleteHashes(email: String) {
        transaction(db) {
            deleteWhere { EMAIL eq email }
        }
    }
}
