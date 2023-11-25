package app.meetacy.backend.feature.telegram.endpoints.integration.await

import app.meetacy.backend.feature.telegram.endpoints.await.AwaitRepository
import app.meetacy.backend.feature.telegram.endpoints.await.AwaitResult
import app.meetacy.backend.feature.telegram.endpoints.await.telegramAwait
import app.meetacy.backend.feature.telegram.usecase.await.TelegramAuthAwaitUsecase
import app.meetacy.backend.feature.telegram.usecase.await.TelegramAuthAwaitUsecase.Result
import app.meetacy.backend.types.serializable.access.AccessToken
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.di.DI
import io.ktor.server.routing.Route

fun Route.telegramAwait(di: DI) {
    val telegramAuthAwaitUsecase: TelegramAuthAwaitUsecase by di.getting

    val repository = object : AwaitRepository {
        override suspend fun await(token: AccessToken): AwaitResult {
            return when (val result = telegramAuthAwaitUsecase.await(token.type())) {
                is Result.TokenInvalid -> AwaitResult.TokenInvalid
                is Result.Success -> AwaitResult.Success(result.token.serializable())
            }
        }
    }

    telegramAwait(repository)
}
