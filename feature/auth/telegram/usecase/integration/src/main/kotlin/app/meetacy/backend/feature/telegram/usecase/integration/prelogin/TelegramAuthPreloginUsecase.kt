package app.meetacy.backend.feature.telegram.usecase.integration.prelogin

import app.meetacy.backend.feature.telegram.database.TelegramAuthStorage
import app.meetacy.backend.feature.telegram.usecase.prelogin.TelegramAuthPreloginUsecase
import app.meetacy.backend.hash.HashGenerator
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.telegramAuthPreloginUsecase() {
    val telegramAuthPreloginUsecase by singleton {
        val telegramAuthStorage: TelegramAuthStorage by getting
        val accessHashGenerator: AccessHashGenerator by getting
        val telegramAuthBotUsername: String? by getting

        val storage = object : TelegramAuthPreloginUsecase.Storage {
            override suspend fun saveTemporalHash(temporalToken: AccessToken, hash: TemporaryTelegramHash) {
                telegramAuthStorage.saveTelegramAuth(temporalToken, hash)
            }
        }

        val telegramHashGenerator = object : TelegramAuthPreloginUsecase.HashGenerator {
            override fun generate(): TemporaryTelegramHash {
                val result = HashGenerator.generate(TemporaryTelegramHash.LENGTH)
                return TemporaryTelegramHash(result)
            }
        }

        val linkProvider = object : TelegramAuthPreloginUsecase.LinkProvider {
            override fun link(hash: TemporaryTelegramHash): String {
                telegramAuthBotUsername ?: return hash.string
                return "https://t.me/${telegramAuthBotUsername}?start=${hash.string}"
            }
        }

        TelegramAuthPreloginUsecase(
            storage = storage,
            telegramHashGenerator = telegramHashGenerator,
            linkProvider = linkProvider,
            tokenGenerator = accessHashGenerator,
        )
    }
}
