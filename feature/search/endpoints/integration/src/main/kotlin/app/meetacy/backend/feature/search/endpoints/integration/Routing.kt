package app.meetacy.backend.feature.search.endpoints.integration

import app.meetacy.backend.feature.search.endpoints.SearchRepository
import app.meetacy.backend.feature.search.endpoints.SearchResult
import app.meetacy.backend.feature.search.endpoints.search
import app.meetacy.backend.feature.search.usecase.SearchUsecase
import app.meetacy.backend.feature.search.usecase.SearchUsecase.Result
import app.meetacy.backend.types.search.SearchItem
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.location.type
import app.meetacy.backend.types.serializable.search.serializable
import io.ktor.server.routing.*
import app.meetacy.di.DI

fun Route.search(di: DI) {
    val searchUsecase: SearchUsecase by di.getting

    val searchRepository = object : SearchRepository {
        override suspend fun search(
            token: AccessIdentity,
            location: Location?,
            prompt: String
        ): SearchResult = when (
            val result = searchUsecase.search(token.type(), location?.type(), prompt)
        ) {
            is Result.InvalidAccessIdentity -> SearchResult.TokenInvalid
            is Result.Success -> SearchResult.Success(
                items = result.results.map(SearchItem::serializable)
            )
        }
    }

    search(searchRepository)
}
