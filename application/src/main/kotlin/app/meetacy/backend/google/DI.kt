package app.meetacy.backend.google

import app.meetacy.di.builder.DIBuilder
import app.meetacy.google.maps.GooglePlace
import app.meetacy.google.maps.GooglePlacesTextSearch
import app.meetacy.google.maps.ktor.KtorGooglePlacesTextSearch
import io.ktor.client.*

fun DIBuilder.google() {
    val googlePlacesTextSearch by singleton<GooglePlacesTextSearch> {
        val googlePlacesToken: String? by getting
        val token = googlePlacesToken

        if (token == null) {
            GooglePlacesTextSearch.NoOp
        } else {
            KtorGooglePlacesTextSearch(token)
        }
    }
}
