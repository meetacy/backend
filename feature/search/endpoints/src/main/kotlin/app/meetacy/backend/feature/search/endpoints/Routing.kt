package app.meetacy.backend.feature.search.endpoints

import app.meetacy.backend.types.serializable.access.AccessIdentity
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class SearchParams(
    val prompt: String
)

interface SearchRepository {
    fun search(
        token: AccessIdentity,
        prompt: String
    ): SearchResult
}

sealed interface SearchResult {
    data object TokenInvalid : SearchResult
    data class Success(val result: SearchResult)
}

fun Route.search() {

}
