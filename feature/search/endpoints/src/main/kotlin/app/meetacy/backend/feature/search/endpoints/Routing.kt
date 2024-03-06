package app.meetacy.backend.feature.search.endpoints

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.latitudeOrNull
import app.meetacy.backend.core.endpoints.longitudeOrNull
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.search.SearchItem
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

interface SearchRepository {
    suspend fun search(
        token: AccessIdentity,
        location: Location?,
        prompt: String
    ): SearchResult
}

sealed interface SearchResult {
    data object TokenInvalid : SearchResult
    data class Success(val items: List<SearchItem>) : SearchResult
}

fun Route.search(repository: SearchRepository) = get("/search") {
    val token = call.accessIdentity()

    val latitude = call.parameters.latitudeOrNull()
    val longitude = call.parameters.longitudeOrNull()

    val prompt: String by call.parameters

    val location = if (latitude != null && longitude != null) Location(latitude, longitude) else null

    when (val result = repository.search(token, location, prompt)) {
        is SearchResult.TokenInvalid -> call.respondFailure(Failure.InvalidToken)
        is SearchResult.Success -> call.respondSuccess(result.items)
    }
}
