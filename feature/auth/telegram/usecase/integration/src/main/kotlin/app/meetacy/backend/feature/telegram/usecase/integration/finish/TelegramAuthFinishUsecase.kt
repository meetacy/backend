package app.meetacy.backend.feature.telegram.usecase.integration.finish

import app.meetacy.backend.feature.auth.usecase.GenerateAuthUsecase
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.feature.telegram.database.TelegramAuthStorage
import app.meetacy.backend.feature.telegram.usecase.finish.TelegramAuthFinishUsecase
import app.meetacy.backend.feature.telegram.usecase.middleware.TelegramAuthMiddleware
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.edit.EditUserUsecase
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.telegram.SecretTelegramBotKey
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.telegramAuthFinishUsecase() {
    val telegramAuthFinishUsecase by singleton {
        val utf8Checker: Utf8Checker by getting
        val telegramAuthStorage: TelegramAuthStorage by getting
        val telegramAuthMiddleware: TelegramAuthMiddleware by getting
        val usersStorage: UsersStorage by getting
        val generateTokenUsecase: GenerateTokenUsecase by getting
        val generateAuthUsecase: GenerateAuthUsecase by getting
        val editUserUsecase: EditUserUsecase by getting
        val secretTelegramBotKey: SecretTelegramBotKey? by getting

        val storage = object : TelegramAuthFinishUsecase.Storage {
            override suspend fun checkTemporalHash(hash: TemporaryTelegramHash): Boolean {
                return telegramAuthStorage.checkTemporalHash(hash)
            }

            override suspend fun getLinkedUserIdOrNull(telegramId: Long): UserId? {
                return usersStorage.getLinkedTelegramUserOrNull(telegramId)?.identity?.id
            }

            override suspend fun generateAuth(nickname: String): AccessIdentity {
                return (generateAuthUsecase.generateAuth(nickname) as GenerateAuthUsecase.Result.Success).accessIdentity
            }

            override suspend fun generateToken(userId: UserId): AccessIdentity {
                return generateTokenUsecase.generateToken(userId)
            }

            override suspend fun setLinkedTelegramId(telegramId: Long, userId: UserId) {
                usersStorage.setLinkedTelegramUser(telegramId, userId)
            }

            override suspend fun saveUsernameSafely(username: Username, userId: UserId) {
                editUserUsecase.editUser(
                    userId = userId,
                    username = Optional.Present(username)
                )
            }

            override suspend fun saveAccessIdentity(
                accessIdentity: AccessIdentity,
                temporalHash: TemporaryTelegramHash
            ) {
                telegramAuthMiddleware.saveAccessIdentity(accessIdentity, temporalHash)
            }
        }

        TelegramAuthFinishUsecase(
            utf8Checker = utf8Checker,
            storage = storage,
            secretBotKey = secretTelegramBotKey
        )
    }
}
