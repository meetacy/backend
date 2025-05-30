@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.users.database.users

import app.meetacy.backend.constants.*
import app.meetacy.backend.feature.users.database.users.UsersTable.ACCESS_HASH
import app.meetacy.backend.feature.users.database.users.UsersTable.AVATAR_ID
import app.meetacy.backend.feature.users.database.users.UsersTable.EMAIL
import app.meetacy.backend.feature.users.database.users.UsersTable.EMAIL_VERIFIED
import app.meetacy.backend.feature.users.database.users.UsersTable.LINKED_TELEGRAM_ID
import app.meetacy.backend.feature.users.database.users.UsersTable.NICKNAME
import app.meetacy.backend.feature.users.database.users.UsersTable.USERNAME
import app.meetacy.backend.feature.users.database.users.UsersTable.USER_ID
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.annotation.UnsafeRawUsername
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.optional.ifPresent
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserIdentity
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.users.username
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UsersTable : Table() {
    val USER_ID = long("USER_ID").autoIncrement()
    val ACCESS_HASH = varchar("ACCESS_HASH", length = ACCESS_HASH_LENGTH)
    val NICKNAME = varchar("NICKNAME", length = NICKNAME_MAX_LIMIT)
    @UnsafeRawUsername
    val USERNAME = varchar("USERNAME", length = USERNAME_MAX_LIMIT).nullable()
    val EMAIL = varchar("EMAIL", length = EMAIL_MAX_LIMIT).nullable()
    val EMAIL_VERIFIED = bool("EMAIL_VERIFIED").default(false)
    val AVATAR_ID = long("AVATAR_ID").nullable()
    val LINKED_TELEGRAM_ID = long("LINKED_TELEGRAM_ID").nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(USER_ID)
}

class UsersStorage(private val db: Database) {

    @OptIn(UnsafeRawUsername::class)
    suspend fun addUser(
        accessHash: AccessHash,
        nickname: String
    ): FullUser = newSuspendedTransaction(Dispatchers.IO, db) {
        val result = UsersTable.insert { statement ->
            statement[ACCESS_HASH] = accessHash.string
            statement[NICKNAME] = nickname
        }
        val avatarId = result[AVATAR_ID]

        return@newSuspendedTransaction FullUser(
            UserIdentity(
                UserId(result[USER_ID]),
                AccessHash(result[ACCESS_HASH])
            ),
            result[NICKNAME],
            result[USERNAME]?.username,
            result[EMAIL],
            result[EMAIL_VERIFIED],
            if (avatarId != null) FileId(avatarId) else null,
            result[LINKED_TELEGRAM_ID],
        )
    }

    suspend fun getUsersOrNull(userIds: List<UserId>): List<FullUser?> = newSuspendedTransaction(Dispatchers.IO, db) {
        val rawUserIds = userIds.map { it.long }

        val foundUsers = UsersTable.select { USER_ID inList rawUserIds }
            .map { it.toUser() }
            .associateBy { user -> user.identity.id }

        return@newSuspendedTransaction userIds.map { foundUsers[it] }
    }

    @OptIn(UnsafeRawUsername::class)
    suspend fun getUserByUsername(username: Username): FullUser? = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersTable.select { USERNAME.lowerCase() eq username.withoutAt.lowercase() }
            .firstOrNull()
            ?.toUser()
    }

    @OptIn(UnsafeRawUsername::class)
    suspend fun searchUsers(
        prefix: String,
        limit: Int
    ): List<FullUser> = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersTable.select {
            (NICKNAME.lowerCase() like "%" + LikePattern.ofLiteral(prefix.lowercase()).pattern + "%") or
                    (USERNAME.lowerCase() like "%" + LikePattern.ofLiteral(prefix.lowercase().removePrefix("@")).pattern + "%")
        }.limit(limit).map { result -> result.toUser() }
    }

    suspend fun isEmailOccupied(
        email: String
    ): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        val result = UsersTable.select { (EMAIL eq email) and EMAIL_VERIFIED }.firstOrNull()
        return@newSuspendedTransaction result != null
    }

    @OptIn(UnsafeRawUsername::class)
    private fun ResultRow.toUser(): FullUser {
        val avatarId = this[AVATAR_ID]
        return FullUser(
            UserIdentity(
                UserId(this[USER_ID]),
                AccessHash(this[ACCESS_HASH])
            ),
            this[NICKNAME],
            this[USERNAME]?.username,
            this[EMAIL],
            this[EMAIL_VERIFIED],
            if (avatarId != null) FileId(avatarId) else null,
            this[LINKED_TELEGRAM_ID],
        )
    }

    suspend fun updateEmail(userIdentity: UserId, email: String) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            UsersTable.update({ USER_ID eq userIdentity.long }) { statement ->
                statement[EMAIL] = email
            }
        }

    suspend fun verifyEmail(userIdentity: UserId) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            UsersTable.update({ USER_ID eq userIdentity.long }) { statement ->
                statement[EMAIL_VERIFIED] = true
            }
        }

    @OptIn(UnsafeRawUsername::class)
    suspend fun editUser(
        userId: UserId,
        nickname: Optional<String>,
        username: Optional<Username?>,
        avatarId: Optional<FileId?>,
    ): FullUser = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersTable.update({ USER_ID eq userId.long }) { statement ->
            nickname.ifPresent {
                statement[NICKNAME] = it
            }
            avatarId.ifPresent {
                statement[AVATAR_ID] = it?.long
            }
            username.ifPresent {
                statement[USERNAME] = it?.withoutAt
            }
        }
        return@newSuspendedTransaction UsersTable.select { USER_ID eq userId.long }
            .first()
            .toUser()
    }

    @OptIn(UnsafeRawUsername::class)
    suspend fun isUsernameOccupied(username: Username): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersTable.select { USERNAME.lowerCase() eq username.withoutAt.lowercase() }.any()
    }

    suspend fun getLinkedTelegramUserOrNull(
        telegramId: Long
    ): FullUser? = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersTable.select { LINKED_TELEGRAM_ID eq telegramId }
            .firstOrNull()
            ?.toUser()
    }

    suspend fun setLinkedTelegramUser(
        telegramId: Long,
        userId: UserId
    ) = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersTable.update({ USER_ID eq userId.long }) { statement ->
            statement[LINKED_TELEGRAM_ID] = telegramId
        }
    }
}
