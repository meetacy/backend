package app.meetacy.backend.application.endpoints

import app.meetacy.backend.feature.auth.endpoints.auth
import app.meetacy.backend.feature.files.endpoints.files
import app.meetacy.backend.feature.friends.endpoints.FriendsDependencies
import app.meetacy.backend.feature.friends.endpoints.friends
import app.meetacy.backend.feature.invitations.endpoints.InvitationsDependencies
import app.meetacy.backend.feature.invitations.endpoints.invitations
import app.meetacy.backend.endpoint.ktor.exceptions.installExceptionsHandler
import app.meetacy.backend.endpoint.ktor.versioning.ApiVersion
import app.meetacy.backend.feature.meetings.endpoints.meetings
import app.meetacy.backend.feature.notifications.endpoints.NotificationsDependencies
import app.meetacy.backend.feature.notifications.endpoints.notifications
import app.meetacy.backend.feature.updates.endpoints.updates.UpdatesDependencies
import app.meetacy.backend.feature.updates.endpoints.updates.updates
import app.meetacy.backend.feature.users.endpoints.users
import app.meetacy.backend.feature.users.endpoints.validate.ValidateUsernameRepository
import app.meetacy.backend.feature.users.endpoints.validate.validateUsername
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

@Suppress("ExtractKtorModule")
@OptIn(ExperimentalSerializationApi::class)
fun prepareEndpoints(
    port: Int,
    friendsDependencies: FriendsDependencies,
    notificationsDependencies: NotificationsDependencies,
    invitationsDependencies: InvitationsDependencies,
    validateUsernameRepository: ValidateUsernameRepository,
    updatesDependencies: UpdatesDependencies
): ApplicationEngine = embeddedServer(CIO, host = "localhost", port = port) {

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
        users()
        meetings()
        friends(friendsDependencies)
        notifications(notificationsDependencies)
        files()
        invitations(invitationsDependencies)
        validateUsername(validateUsernameRepository)
        updates(updatesDependencies)
    }
}
