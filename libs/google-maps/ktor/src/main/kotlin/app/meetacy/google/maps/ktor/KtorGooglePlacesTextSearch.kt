package app.meetacy.google.maps.ktor

import app.meetacy.google.maps.GooglePlace
import app.meetacy.google.maps.GooglePlacesTextSearch
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

class KtorGooglePlacesTextSearch(
    private val token: String,
    httpClient: HttpClient = HttpClient()
) : GooglePlacesTextSearch {
    private val httpClient = httpClient.config {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        Logging {
            level = LogLevel.NONE
            logger = object : Logger {
                override fun log(message: String) = println(message)
            }
        }
    }

    override suspend fun search(
        location: GooglePlace.Location,
        query: String
    ): List<GooglePlace> = coroutineScope {
        httpClient.get("https://maps.googleapis.com/maps/api/place/textsearch/json") {
            url {
                parameters.append("query", query)
                parameters.append("key", token)
                parameters.append("location", "${location.latitude},${location.longitude}")
            }
        }
            .body<GooglePlacesMultiResponse>()
            .let { response ->
                response.copy(
                    results = response.results.mapNotNull { result ->
                        result.placeId ?: return@mapNotNull null
                        async { getDetails(result.placeId) }
                    }.awaitAll().filterNotNull()
                )
            }.map()
    }

    suspend fun getDetails(placeId: String): GooglePlacesResult? =
        httpClient.get("https://maps.googleapis.com/maps/api/place/details/json") {
            url {
                parameters.append("key", token)
                parameters.append("place_id", placeId)
            }
        }.body<GooglePlacesSingleResponse>().result
}
