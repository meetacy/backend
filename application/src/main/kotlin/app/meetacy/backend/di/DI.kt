@file:Suppress("NAME_SHADOWING")

package app.meetacy.backend.di

import app.meetacy.backend.application.database.DatabaseConfig
import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.usecase.usecase
import app.meetacy.backend.discord.discord
import app.meetacy.backend.google.google
import app.meetacy.backend.ktor.ktor
import app.meetacy.backend.types.files.FileSize
import app.meetacy.backend.types.integration.types
import app.meetacy.di.builder.di
import app.meetacy.discord.webhook.DiscordWebhook
import app.meetacy.google.maps.GooglePlacesTextSearch
import kotlinx.coroutines.CoroutineScope

fun buildDI(
    port: Int,
    coroutineScope: CoroutineScope,
    databaseConfig: DatabaseConfig,
    fileBasePath: String,
    fileSizeLimit: FileSize,
    discordWebhook: DiscordWebhook?,
    googlePlacesToken: String?,
    mockGooglePlacesSearch: GooglePlacesTextSearch? = null
) = di {
    val port by constant(port)
    val databaseConfig by constant(databaseConfig)
    val fileBasePath by constant(fileBasePath)
    val fileSizeLimit by constant(fileSizeLimit)
    val discordWebhook by constant(discordWebhook)
    val googlePlacesToken by constant(googlePlacesToken)
    val coroutineScope by constant(coroutineScope)
    val mockGooglePlacesSearch by constant(mockGooglePlacesSearch)

    usecase()
    database()
    types()
    // libs
    ktor()
    discord()
    google()
}
