package app.meetacy.backend.feature.telegram.usecase.integration.await

import app.meetacy.backend.feature.telegram.database.TelegramAuthStorage
import app.meetacy.backend.feature.telegram.usecase.await.TelegramAuthAwaitUsecase
import app.meetacy.backend.feature.telegram.usecase.middleware.TelegramAuthMiddleware
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.telegramAuthAwaitUsecase() {
    val telegramAuthAwaitUsecase by singleton {
        val telegramAuthStorage: TelegramAuthStorage by getting
        val telegramAuthMiddleware: TelegramAuthMiddleware by getting

        val storage = object : TelegramAuthAwaitUsecase.Storage {
            override suspend fun getTemporalHash(temporalToken: AccessToken): TemporaryTelegramHash? {
                return telegramAuthStorage.getTemporalHash(temporalToken)
            }

            override suspend fun getAccessIdentity(telegramHash: TemporaryTelegramHash): AccessIdentity {
                return telegramAuthMiddleware.getAccessIdentity(telegramHash)
            }
        }

        TelegramAuthAwaitUsecase(storage)
    }
}
