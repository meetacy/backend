@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.users

import app.meetacy.backend.database.types.*
import app.meetacy.backend.types.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UsersTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val NICKNAME = varchar("NICKNAME", length = NICKNAME_MAX_LIMIT)
    private val EMAIL = varchar("EMAIL", length = EMAIL_MAX_LIMIT).nullable()
    private val EMAIL_VERIFIED = bool("EMAIL_VERIFIED").default(false)

    init {
        transaction(db) {
            SchemaUtils.create(this@UsersTable)
        }
    }

    fun addUser(
        accessHash: AccessHash,
        nickname: String
    ): DatabaseUser = transaction(db) {
        val result = insert { statement ->
            statement[ACCESS_HASH] = accessHash.string
            statement[NICKNAME] = nickname
        }
        return@transaction DatabaseUser(
            UserId(result[USER_ID]),
            AccessHash(result[ACCESS_HASH]),
            result[NICKNAME],
            result[EMAIL],
            result[EMAIL_VERIFIED]
        )
    }

    fun getUser(
        userId: UserId
    ): DatabaseUser? = transaction(db) {
       val result = select { USER_ID eq userId.long }.firstOrNull() ?: return@transaction null
       return@transaction result.toUser()
    }

    fun getUsers(userIds: List<UserId>): List<DatabaseUser> {

    }

    fun isEmailOccupied(
        email: String
    ): Boolean = transaction(db) {
       val result = select { (EMAIL eq email) and EMAIL_VERIFIED}.firstOrNull()
       return@transaction result != null
    }

    private fun ResultRow.toUser(): DatabaseUser =
        DatabaseUser(
            UserId(this[USER_ID]),
            AccessHash(this[ACCESS_HASH]),
            this[NICKNAME],
            this[EMAIL],
            this[EMAIL_VERIFIED]
        )

    fun updateEmail(
        userId: UserId,
        email: String
    ) {
        transaction(db) {
            update({ USER_ID eq userId.long }) {statement ->
                statement[EMAIL] = email
            }
        }
    }

    fun verifyEmail(
        userId: UserId
    ) {
        transaction(db) {
            update({ USER_ID eq userId.long }) { statement ->
                statement[EMAIL_VERIFIED] = true
            }
        }
    }
}
