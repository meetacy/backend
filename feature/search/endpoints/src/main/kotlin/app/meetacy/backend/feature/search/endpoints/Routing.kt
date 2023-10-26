package app.meetacy.backend.feature.search.endpoints

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.location.Location
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import app.meetacy.backend.types.serializable.search.SearchItem as SearchItem

interface SearchRepository {
    suspend fun search(
        token: AccessIdentity,
        location: Location,
        prompt: String
    ): SearchResult
}

sealed interface SearchResult {
    data object TokenInvalid : SearchResult
    data class Success(val items: List<SearchItem>) : SearchResult
}

fun Route.search(repository: SearchRepository) = get("/search") {
    val token = call.accessIdentity()

    val prompt: String by call.parameters
    val latitude: Double by call.parameters
    val longitude: Double by call.parameters

    when (val result = repository.search(token, Location(latitude, longitude), prompt)) {
        is SearchResult.TokenInvalid -> call.respondFailure(Failure.InvalidToken)
        is SearchResult.Success -> call.respondSuccess(result.items)
    }
}
