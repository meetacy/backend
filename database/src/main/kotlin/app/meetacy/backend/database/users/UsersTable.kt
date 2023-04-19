@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.users

import app.meetacy.backend.database.types.DatabaseUser
import app.meetacy.backend.types.*
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UsersTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val NICKNAME = varchar("NICKNAME", length = NICKNAME_MAX_LIMIT)
    private val USERNAME = varchar("USERNAME", length = USERNAME_MAX_LIMIT).nullable()
    private val EMAIL = varchar("EMAIL", length = EMAIL_MAX_LIMIT).nullable()
    private val EMAIL_VERIFIED = bool("EMAIL_VERIFIED").default(false)
    private val AVATAR_ID = long("AVATAR_ID").nullable()


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

        return@newSuspendedTransaction DatabaseUser(
            UserIdentity(
                UserId(result[USER_ID]),
                AccessHash(result[ACCESS_HASH])
            ),
            result[NICKNAME],
            result[USERNAME],
            result[EMAIL],
            result[EMAIL_VERIFIED],
            if (avatarId != null) FileId(avatarId) else null
        )
    }

    suspend fun getUsersOrNull(userIds: List<UserId>): List<DatabaseUser?> = newSuspendedTransaction(db = db) {
        val rawUserIds = userIds.map { it.long }

        val foundUsers = select { USER_ID inList rawUserIds }
            .map { it.toUser() }
            .associateBy { user -> user.identity.userId }

        return@newSuspendedTransaction userIds.map { foundUsers[it] }
    }


    suspend fun  isEmailOccupied(
        email: String
    ): Boolean = newSuspendedTransaction(db = db) {
       val result = select { (EMAIL eq email) and EMAIL_VERIFIED}.firstOrNull()
       return@newSuspendedTransaction result != null
    }

    private fun ResultRow.toUser(): DatabaseUser {
        val avatarId = this[AVATAR_ID]
        return DatabaseUser(
            UserIdentity(
                UserId(this[USER_ID]),
                AccessHash(this[ACCESS_HASH])
            ),
            this[NICKNAME],
            this[USERNAME],
            this[EMAIL],
            this[EMAIL_VERIFIED],
            if (avatarId != null) FileId(avatarId) else null
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

    suspend fun editUser(
        userId: UserId,
        nickname: String?,
        username: Optional<String?>,
        avatarId: Optional<FileId?>,
    ): DatabaseUser? = newSuspendedTransaction(db = db) {
        if (!checkUsername(username)) return@newSuspendedTransaction null
        update({ USER_ID eq userId.long }) { statement ->
            nickname?.let { statement[NICKNAME] = it }
            avatarId.ifPresent {
                statement[AVATAR_ID] = it?.long
            }
            username.ifPresent {
                statement[USERNAME] = it
            }
        }
        return@newSuspendedTransaction select { USER_ID eq userId.long }
            .first<ResultRow>()
            .toUser()
    }

    suspend fun checkUsername(username: Optional<String?>): Boolean = newSuspendedTransaction(db = db) {
        return@newSuspendedTransaction select { USERNAME eq username.ifPresent { it } }.firstOrNull() == null
    }
}
