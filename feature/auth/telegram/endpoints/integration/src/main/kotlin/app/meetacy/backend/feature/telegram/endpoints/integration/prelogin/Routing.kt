package app.meetacy.backend.feature.telegram.endpoints.integration.prelogin

import app.meetacy.backend.feature.telegram.endpoints.prelogin.PreloginRepository
import app.meetacy.backend.feature.telegram.endpoints.prelogin.TelegramPrelogin
import app.meetacy.backend.feature.telegram.endpoints.prelogin.telegramPrelogin
import app.meetacy.backend.feature.telegram.usecase.GenerateTelegramTemporaryTokenUsecase
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.Route

internal fun Route.telegramPrelogin(di: DI) {
    val generateTokenUsecase: GenerateTelegramTemporaryTokenUsecase by di.getting

    val preloginRepository = object : PreloginRepository {
        override suspend fun telegramPrelogin(): TelegramPrelogin {
            val result = generateTokenUsecase.generateToken()
            return TelegramPrelogin(
                token = result.temporalToken.serializable(),
                botLink = result.telegramLink
            )
        }
    }

    telegramPrelogin(preloginRepository)
}
