@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.users

import app.meetacy.backend.database.types.DatabaseUser
import app.meetacy.backend.types.*
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.gender.UserGender
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UsersTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val GENDER = varchar("GENDER", length = GENDER_NAME_MAX_LIMIT).nullable()
    private val NICKNAME = varchar("NICKNAME", length = NICKNAME_MAX_LIMIT)
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

        // todo я бы писал [fieldName] = .... Так будет норм пон. Еще если дефолтные будут то не будет ошибок
        return@newSuspendedTransaction DatabaseUser(
            identity = UserIdentity(
                UserId(result[USER_ID]),
                AccessHash(result[ACCESS_HASH])
            ),
            gender = result[GENDER]?.let { UserGender.parseByName(it) },
            nickname = result[NICKNAME],
            email = result[EMAIL],
            emailVerified = result[EMAIL_VERIFIED],
            avatarId = if (avatarId != null) FileId(avatarId) else null
        )
    }

    suspend fun getUsersOrNull(userIds: List<UserId>): List<DatabaseUser?> = newSuspendedTransaction(db = db) {
        val rawUserIds = userIds.map { it.long }

        val foundUsers = select { USER_ID inList rawUserIds }
            .map { it.toUser() }
            .associateBy { user -> user.identity.userId }

        return@newSuspendedTransaction userIds.map { foundUsers[it] }
        // todo лист конвертируется в мап с ключами, мапа не используется. Можно создать foundUsers: List<>,
        //  и отдельно foundUsersWithIds, и в данном случае возвращать foundUsers. Потом мб пригодиться и вторая
    }


    suspend fun isEmailOccupied(
        email: String
    ): Boolean = newSuspendedTransaction(db = db) {
       val result = select { (EMAIL eq email) and EMAIL_VERIFIED}.firstOrNull()
       return@newSuspendedTransaction result != null
    }

    private fun ResultRow.toUser(): DatabaseUser {
        val avatarId = this[AVATAR_ID]
        // todo я бы писал [fieldName] = .... Так будет норм пон. Еще если дефолтные будут то не будет ошибок
        return DatabaseUser(
            identity = UserIdentity(
                UserId(this[USER_ID]),
                AccessHash(this[ACCESS_HASH])
            ),
            gender = this[GENDER]?.let { UserGender.parseByName(it) },
            nickname = this[NICKNAME],
            email = this[EMAIL],
            emailVerified = this[EMAIL_VERIFIED],
            avatarId = if (avatarId != null) FileId(avatarId) else null
        )
    }

    // todo fix: не userIdentity а userId
    suspend fun updateEmail(userId: UserId, email: String) =
        newSuspendedTransaction(db = db) {
            update({ USER_ID eq userId.long }) { statement ->
                statement[EMAIL] = email
            }
        }

    // todo fix: не userIdentity а userId
    suspend fun verifyEmail(userId: UserId) =
        newSuspendedTransaction(db = db) {
            update({ USER_ID eq userId.long }) { statement ->
                statement[EMAIL_VERIFIED] = true
            }
        }

    // todo еще надо unverifyEmail пон

    // todo fix: поч не добавить значения по умолчанию, чтобы с добавлением новых полей не приходилось везде писать
    //  Optional.Undefined и null
    suspend fun editUser(
        userId: UserId,
        gender: Optional<UserGender?> = Optional.Undefined,
        nickname: String? = null,
        avatarId: Optional<FileId?> = Optional.Undefined,
    ): DatabaseUser = newSuspendedTransaction(db = db) {
        update({ USER_ID eq userId.long }) { statement ->
            gender.ifPresent {
                statement[GENDER] = it?.genderName
            }
            nickname?.let { statement[NICKNAME] = it }
            avatarId.ifPresent {
                statement[AVATAR_ID] = it?.long
            }
        }
        return@newSuspendedTransaction select { USER_ID eq userId.long }
            .first<ResultRow>()
            .toUser()
    }
}
