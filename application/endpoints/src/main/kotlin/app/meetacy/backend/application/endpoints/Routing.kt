package app.meetacy.backend.application.endpoints

import app.meetacy.backend.endpoint.ktor.exceptions.installExceptionsHandler
import app.meetacy.backend.endpoint.ktor.versioning.ApiVersion
import app.meetacy.backend.feature.auth.endpoints.integration.auth
import app.meetacy.backend.feature.files.endpoints.integration.files
import app.meetacy.backend.feature.friends.endpoints.integration.friends
import app.meetacy.backend.feature.invitations.endpoints.integration.invitations
import app.meetacy.backend.feature.meetings.endpoints.integration.meetings
import app.meetacy.backend.feature.notifications.endpoints.notifications
import app.meetacy.backend.feature.updates.endpoints.updates.updates
import app.meetacy.backend.feature.users.endpoints.users
import app.meetacy.backend.feature.users.endpoints.validate.validateUsername
import app.meetacy.di.global.di
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.partialcontent.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.rsocket.kotlin.ktor.server.RSocketSupport
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
@Suppress("ExtractKtorModule")
fun prepareEndpoints(): ApplicationEngine {
    val port: Int by di.getting

    return embeddedServer(CIO, host = "localhost", port = port) {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                }
            )
        }
        install(CORS) {
            allowCredentials = true
            anyHost()
            allowHeader(HttpHeaders.ContentType)
            allowHeader(ApiVersion.Header)
        }
        install(PartialContent)
        install(AutoHeadResponse)
        installExceptionsHandler()
        install(WebSockets)
        install(RSocketSupport)

        routing {
            static("/") {
                resources()
            }
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
            auth()
            files()
            users()
            meetings()
            friends()
            notifications()
            invitations()
            validateUsername()
            updates()
        }
    }
}
