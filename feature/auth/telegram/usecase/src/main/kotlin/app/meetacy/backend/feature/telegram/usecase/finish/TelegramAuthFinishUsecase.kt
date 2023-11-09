package app.meetacy.backend.feature.telegram.usecase.finish

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.backend.types.utf8Checker.checkString

class TelegramAuthFinishUsecase(
    private val utf8Checker: Utf8Checker,
    private val storage: Storage
) {

    sealed interface Result {
        data object Success : Result
        data object InvalidHash : Result
        data object InvalidUtf8String : Result
    }

    suspend fun finish(
        temporalHash: TemporaryTelegramHash,
        telegramId: Long,
        username: String?,
        firstName: String?,
        lastName: String?
    ): Result {
        if (!storage.checkTemporalHash(temporalHash)) return Result.InvalidHash

        listOfNotNull(
            username,
            firstName,
            lastName
        ).forEach { string ->
            utf8Checker.checkString(string) { return Result.InvalidUtf8String }
        }

        val userId = storage.getLinkedUserIdOrNull(telegramId)

        val accessIdentity = if (userId == null) {
            val nickname = listOfNotNull(firstName, lastName).joinToString(separator = " ")
            val newAccessIdentity = storage.generateAuth(nickname)
            val parsedUsername = username?.let(Username::parseOrNull)

            if (parsedUsername != null) {
                storage.saveUsernameSafely(parsedUsername, newAccessIdentity.userId)
                storage.setLinkedTelegramId(telegramId, newAccessIdentity.userId)
            }

            newAccessIdentity
        } else {
            storage.generateToken(userId)
        }

        storage.saveAccessIdentity(accessIdentity, temporalHash)

        return Result.Success
    }

    interface Storage {
        suspend fun checkTemporalHash(
            hash: TemporaryTelegramHash
        ): Boolean

        suspend fun getLinkedUserIdOrNull(
            telegramId: Long
        ): UserId?

        suspend fun generateAuth(
            nickname: String
        ): AccessIdentity

        suspend fun generateToken(
            userId: UserId
        ): AccessIdentity

        suspend fun setLinkedTelegramId(
            telegramId: Long,
            userId: UserId
        )

        suspend fun saveUsernameSafely(
            username: Username,
            userId: UserId
        )

        suspend fun saveAccessIdentity(
            accessIdentity: AccessIdentity,
            temporalHash: TemporaryTelegramHash
        )
    }
}
