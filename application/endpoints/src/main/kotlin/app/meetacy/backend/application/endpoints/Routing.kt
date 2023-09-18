package app.meetacy.backend.application.endpoints

import app.meetacy.backend.database.initDatabase
import app.meetacy.backend.endpoint.ktor.exceptions.installExceptionsHandler
import app.meetacy.backend.endpoint.ktor.versioning.ApiVersion
import app.meetacy.backend.feature.auth.endpoints.integration.auth
import app.meetacy.backend.feature.files.endpoints.integration.files
import app.meetacy.backend.feature.friends.endpoints.integration.friends
import app.meetacy.backend.feature.invitations.endpoints.integration.invitations
import app.meetacy.backend.feature.meetings.endpoints.integration.meetings
import app.meetacy.backend.feature.notifications.endpoints.integration.notifications
import app.meetacy.backend.feature.updates.endpoints.integration.updates
import app.meetacy.backend.feature.users.endpoints.integration.users
import app.meetacy.di.DI
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
import org.jetbrains.exposed.sql.Database

@OptIn(ExperimentalSerializationApi::class)
@Suppress("ExtractKtorModule")
suspend fun prepareEndpoints(di: DI): ApplicationEngine {
    val port: Int by di.getting
    val database: Database by di.getting

    initDatabase(database)

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
            staticResources("/", null)
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
            auth(di)
            files(di)
            users(di)
            meetings(di)
            friends(di)
            invitations(di)
            notifications(di)
            updates(di)
            users(di)
        }
    }
}
