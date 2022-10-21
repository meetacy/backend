@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.users

import app.meetacy.backend.database.types.*
import app.meetacy.backend.types.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UsersTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val NICKNAME = varchar("NICKNAME", length = NICKNAME_MAX_LIMIT)
    private val EMAIL = varchar("EMAIL", length = EMAIL_MAX_LIMIT).nullable()
    private val EMAIL_VERIFIED = bool("EMAIL_VERIFIED").default(false)
    private val AVATAR_ID = long("AVATAR_ID").nullable()
    private val AVATAR_HASH = varchar("AVATAR_HASH", length = HASH_LENGTH).nullable()


    init {
        transaction(db) {
            SchemaUtils.create(this@UsersTable)
        }
    }

    suspend fun addUser(
        accessHash: AccessHash,
        nickname: String
    ): DatabaseUser = newSuspendedTransaction(db = db) {
        val result = insert { statement ->
            statement[ACCESS_HASH] = accessHash.string
            statement[NICKNAME] = nickname
        }
        val avatarId = result[AVATAR_ID]
        val avatarHash = result[AVATAR_HASH]
        val avatarIdentity = if (avatarId != null && avatarHash != null) {
            FileIdentity(FileId(avatarId), AccessHash(avatarHash))
        } else null

        return@newSuspendedTransaction DatabaseUser(
            UserIdentity(
                UserId(result[USER_ID]),
                AccessHash(result[ACCESS_HASH])
            ),
            result[NICKNAME],
            result[EMAIL],
            result[EMAIL_VERIFIED],
            avatarIdentity
        )
    }

    suspend fun getUsersOrNull(userIds: List<UserId>): List<DatabaseUser?> = newSuspendedTransaction(db = db) {
        val rawUserIds = userIds.map { it.long }

        val foundUsers = select { USER_ID inList rawUserIds }
            .map { it.toUser() }
            .associateBy { user -> user.identity.userId }

        return@newSuspendedTransaction userIds.map { foundUsers[it] }
    }


    suspend fun isEmailOccupied(
        email: String
    ): Boolean = newSuspendedTransaction(db = db) {
       val result = select { (EMAIL eq email) and EMAIL_VERIFIED}.firstOrNull()
       return@newSuspendedTransaction result != null
    }

    private fun ResultRow.toUser(): DatabaseUser {
        val avatarId = this[AVATAR_ID]
        val avatarHash = this[AVATAR_HASH]
        val avatarIdentity = if (avatarId != null && avatarHash != null) {
            FileIdentity(FileId(avatarId), AccessHash(avatarHash))
        } else null
        return DatabaseUser(
            UserIdentity(
                UserId(this[USER_ID]),
                AccessHash(this[ACCESS_HASH])
            ),
            this[NICKNAME],
            this[EMAIL],
            this[EMAIL_VERIFIED],
            avatarIdentity
        )
    }

    suspend fun updateEmail(userIdentity: UserId, email: String) =
        newSuspendedTransaction(db = db) {
            update({ USER_ID eq userIdentity.long }) { statement ->
                statement[EMAIL] = email
            }
        }

    suspend fun verifyEmail(userIdentity: UserId) =
        newSuspendedTransaction(db = db) {
            update({ USER_ID eq userIdentity.long }) { statement ->
                statement[EMAIL_VERIFIED] = true
            }
        }

    suspend fun addAvatar(accessIdentity: AccessIdentity, avatarIdentity: FileIdentity) =
        newSuspendedTransaction(db = db) {
            update({ USER_ID eq accessIdentity.userId.long }) {statement ->
                statement[AVATAR_ID] = avatarIdentity.fileId.long
                statement[AVATAR_HASH] = avatarIdentity.accessHash.string
            }
        }
}
