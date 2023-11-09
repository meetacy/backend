package app.meetacy.backend.feature.telegram.endpoints.integration.finish

import app.meetacy.backend.feature.telegram.endpoints.finish.FinishRepository
import app.meetacy.backend.feature.telegram.endpoints.finish.FinishResult
import app.meetacy.backend.feature.telegram.endpoints.finish.finish
import app.meetacy.backend.feature.telegram.usecase.finish.TelegramAuthFinishUsecase
import app.meetacy.backend.types.serializable.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.serializable.auth.telegram.type
import app.meetacy.di.DI
import io.ktor.server.routing.Route

internal fun Route.telegramFinish(di: DI) {
    val authFinishUsecase: TelegramAuthFinishUsecase by di.getting

    val finishRepository = object : FinishRepository {
        override suspend fun finish(
            temporalHash: TemporaryTelegramHash,
            telegramId: Long,
            username: String?,
            firstName: String?,
            lastName: String?
        ): FinishResult {
            return when (
                authFinishUsecase.finish(
                    temporalHash.type(),
                    telegramId,
                    username,
                    firstName,
                    lastName
                )
            ) {
                TelegramAuthFinishUsecase.Result.Success -> FinishResult.Success
                TelegramAuthFinishUsecase.Result.InvalidHash -> FinishResult.InvalidHash
                TelegramAuthFinishUsecase.Result.InvalidUtf8String ->  FinishResult.InvalidUtf8String
            }
        }
    }

    finish(finishRepository)
}
