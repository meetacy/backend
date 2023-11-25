package app.meetacy.backend.feature.telegram.endpoints.integration.prelogin

import app.meetacy.backend.feature.telegram.endpoints.prelogin.PreloginRepository
import app.meetacy.backend.feature.telegram.endpoints.prelogin.PreloginResult
import app.meetacy.backend.feature.telegram.endpoints.prelogin.prelogin
import app.meetacy.backend.feature.telegram.usecase.prelogin.TelegramAuthPreloginUsecase
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.Route

internal fun Route.telegramPrelogin(di: DI) {
    val telegramAuthPrelogin: TelegramAuthPreloginUsecase by di.getting

    val preloginRepository = object : PreloginRepository {
        override suspend fun prelogin(): PreloginResult {
            val result = telegramAuthPrelogin.prelogin()
            return PreloginResult(
                token = result.temporalToken.serializable(),
                botLink = result.telegramLink
            )
        }
    }

    prelogin(preloginRepository)
}
