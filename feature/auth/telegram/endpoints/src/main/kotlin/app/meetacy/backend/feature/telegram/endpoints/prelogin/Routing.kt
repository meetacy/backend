package app.meetacy.backend.feature.telegram.endpoints.prelogin

import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessHash
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable

@Serializable
data class TelegramPrelogin(
    val token: AccessHash,
    val botLink: String
)

interface PreloginRepository {
    suspend fun telegramPrelogin(): TelegramPrelogin
}

fun Route.telegramPrelogin(repository: PreloginRepository) {
    get("/prelogin") {
        val result = repository.telegramPrelogin()
        call.respondSuccess(result)
    }
}
