package app.meetacy.backend.feature.telegram.usecase.integration

import app.meetacy.backend.feature.telegram.database.TelegramAuthStorage
import app.meetacy.backend.feature.telegram.usecase.GenerateTelegramTemporaryTokenUsecase
import app.meetacy.backend.hash.HashGenerator
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.prelogin.TemporaryTelegramHash
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.generateTelegramTemporaryTokenUsecase() {
    val generateTelegramTemporaryTokenUsecase by singleton {
        val telegramAuthStorage: TelegramAuthStorage by getting
        val accessHashGenerator: AccessHashGenerator by getting
        val telegramAuthBotUsername: String? by getting

        val storage = object : GenerateTelegramTemporaryTokenUsecase.Storage {
            override suspend fun saveTemporalToken(temporalToken: AccessToken, telegramHash: TemporaryTelegramHash) {
                telegramAuthStorage.saveTelegramAuthInfo(temporalToken, telegramHash)
            }
        }

        val telegramHashGenerator = object : GenerateTelegramTemporaryTokenUsecase.HashGenerator {
            override fun generate(): TemporaryTelegramHash {
                val result = HashGenerator.generate(TemporaryTelegramHash.LENGTH)
                return TemporaryTelegramHash(result)
            }
        }

        val linkProvider = object : GenerateTelegramTemporaryTokenUsecase.LinkProvider {
            override fun link(token: TemporaryTelegramHash): String {
                telegramAuthBotUsername ?: return token.string
                return "https://t.me/${telegramAuthBotUsername}?start=${token.string}"
            }
        }

        GenerateTelegramTemporaryTokenUsecase(
            storage = storage,
            telegramHashGenerator = telegramHashGenerator,
            linkProvider = linkProvider,
            tokenGenerator = accessHashGenerator,
        )
    }
}
