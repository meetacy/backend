package app.meetacy.backend.feature.telegram.endpoints.prelogin

import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessToken
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable

@Serializable
data class PreloginResult(
    val token: AccessToken,
    val botLink: String
)

interface PreloginRepository {
    suspend fun prelogin(): PreloginResult
}

fun Route.prelogin(repository: PreloginRepository) {
    get("/prelogin") {
        val result = repository.prelogin()
        call.respondSuccess(result)
    }
}
