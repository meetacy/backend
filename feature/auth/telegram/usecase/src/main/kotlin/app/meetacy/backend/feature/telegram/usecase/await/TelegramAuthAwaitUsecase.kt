package app.meetacy.backend.feature.telegram.usecase.await

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash

class TelegramAuthAwaitUsecase(
    private val storage: Storage
) {

    suspend fun await(temporalToken: AccessToken): Result {
        val telegramHash = storage.getTemporalHash(temporalToken)
            ?: return Result.TokenInvalid
        val token = storage.getAccessIdentity(telegramHash)
        return Result.Success(token)
    }

    sealed interface Result {
        data class Success(val token: AccessIdentity) : Result
        data object TokenInvalid : Result
    }

    interface Storage {
        suspend fun getTemporalHash(temporalToken: AccessToken): TemporaryTelegramHash?
        suspend fun getAccessIdentity(telegramHash: TemporaryTelegramHash): AccessIdentity
    }
}
