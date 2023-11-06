package app.meetacy.backend.feature.telegram.usecase.await

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.access.AccessToken
import app.meetacy.backend.types.auth.telegram.TemporaryTelegramHash
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.types.users.getUserView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.Deferred as KotlinxDeferred

class TelegramAuthAwaitUsecase(
    private val storage: Storage,
    private val getUsersViewsRepository: GetUsersViewsRepository
) {
    // todo: рассмотреть возможность добавления инкрементального
    //  идентификатора (заменить AccessToken на AccessIdentity),
    //  т.к. эти токены будут жить долго (возможно вечно). Ведь
    //  может произойти такое, что у тебя регистрация завершена давно,
    //  а в приложение ты не заходил, это надо захендлить.
    //  А их могут брутфорсить
    suspend fun await(
        scope: CoroutineScope,
        temporalToken: AccessToken
    ): Result {
        val telegramHash = storage.getTelegramHash(temporalToken)
            ?: return Result.InvalidToken

        return Result.Deferred(
            value = scope.async {
                val token = storage.getAccessIdentity(telegramHash)
                val user = getUsersViewsRepository.getUserView(
                    viewerId = token.userId,
                    userId = token.userId
                )
                Result.Success(token, user)
            }
        )
    }

    sealed interface Result {

        data class Success(
            val token: AccessIdentity,
            val user: UserView
        )

        data class Deferred(val value: KotlinxDeferred<Success>) : Result

        data object InvalidToken : Result
    }

    interface Storage {
        fun getTelegramHash(temporalToken: AccessToken): TemporaryTelegramHash?
        suspend fun getAccessIdentity(telegramHash: TemporaryTelegramHash): AccessIdentity
    }
}
