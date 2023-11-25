package app.meetacy.backend.feature.telegram.usecase.integration.middleware

import app.meetacy.backend.feature.telegram.database.TelegramAuthStorage
import app.meetacy.backend.feature.telegram.usecase.middleware.TelegramAuthMiddleware
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.telegramAuthMiddleware() {
    val telegramAuthMiddleware by singleton {
        val telegramAuthStorage: TelegramAuthStorage by getting

        val storage = object : TelegramAuthMiddleware.Storage {
            override suspend fun addAccessIdentity(
                telegramHash: TemporaryTelegramHash,
                accessIdentity: AccessIdentity
            ) {
                telegramAuthStorage.saveAccessIdentity(accessIdentity, telegramHash)
            }

            override suspend fun getAccessIdentity(telegramHash: TemporaryTelegramHash): AccessIdentity? {
                return telegramAuthStorage.getAccessIdentity(telegramHash)
            }
        }

        TelegramAuthMiddleware(storage)
    }
}
