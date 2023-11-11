package app.meetacy.backend.feature.telegram.endpoints.finish

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.auth.telegram.SecretTelegramBotKey
import app.meetacy.backend.types.serializable.auth.telegram.TemporaryTelegramHash
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

@Serializable
data class FinishParams(
    val temporalHash: TemporaryTelegramHash,
    val secretBotKey: SecretTelegramBotKey,
    val telegramId: Long,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
)

sealed interface FinishResult {
    data object Success : FinishResult
    data object InvalidHash : FinishResult
    data object InvalidUtf8String : FinishResult
}

interface FinishRepository {
    suspend fun finish(
        temporalHash: TemporaryTelegramHash,
        telegramId: Long,
        username: String?,
        firstName: String?,
        lastName: String?,
    ): FinishResult
}

fun Route.finish(repository: FinishRepository) {
    post("/finish") {
        val params = call.receive<FinishParams>()

        when (
            repository.finish(
                params.temporalHash,
                params.telegramId,
                params.username,
                params.firstName,
                params.lastName
            )
        ) {
            is FinishResult.Success -> call.respondSuccess()
            FinishResult.InvalidHash -> call.respondFailure(Failure.InvalidTelegramTemporaryHash)
            FinishResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        }
    }
}
