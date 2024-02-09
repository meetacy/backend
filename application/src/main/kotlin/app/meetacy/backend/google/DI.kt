package app.meetacy.backend.google

import app.meetacy.di.builder.DIBuilder
import app.meetacy.google.maps.GooglePlacesTextSearch
import app.meetacy.google.maps.ktor.KtorGooglePlacesTextSearch
import io.ktor.client.*

fun DIBuilder.google() {
    val googlePlacesTextSearch by singleton<GooglePlacesTextSearch> {
        val googlePlacesToken: String? by getting
        val httpClient: HttpClient by getting
        val mockGooglePlacesTextSearch: GooglePlacesTextSearch? by getting

        val token = googlePlacesToken
        val search = mockGooglePlacesTextSearch

        search ?: if (token != null) {
            KtorGooglePlacesTextSearch(
                token = token,
                httpClient = httpClient
            )
        } else {
            GooglePlacesTextSearch.NoOp
        }
    }
}
