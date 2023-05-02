package app.meetacy.backend.endpoint

import app.meetacy.backend.endpoint.auth.AuthDependencies
import app.meetacy.backend.endpoint.auth.auth
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.endpoint.files.files
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.endpoint.friends.friends
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.meetings.meetings
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.notifications.notifications
import app.meetacy.backend.endpoint.users.UsersDependencies
import app.meetacy.backend.endpoint.users.users
import app.meetacy.backend.endpoint.versioning.ApiVersion
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
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@Suppress("ExtractKtorModule")
@OptIn(ExperimentalSerializationApi::class)
fun startEndpoints(
    port: Int,
    wait: Boolean,
    authDependencies: AuthDependencies,
    friendsDependencies: FriendsDependencies,
    meetingsDependencies: MeetingsDependencies,
    notificationsDependencies: NotificationsDependencies,
    filesDependencies: FilesDependencies,
    usersDependencies: UsersDependencies
): ApplicationEngine = embeddedServer(CIO, host = "localhost", port = port) {

    install(ContentNegotiation) {
        json(
            Json {
                explicitNulls = false
            }
        )
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(ApiVersion.Header)
    }
    install(AutoHeadResponse)
    install(PartialContent) {
        // Maximum number of ranges that will be accepted from an HTTP request.
        // If the HTTP request specifies more ranges, they will all be merged into a single range.
        maxRangeCount = 10
    }

    routing {
        static("/") {
            resources()
        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        auth(authDependencies)
        users(usersDependencies)
        meetings(meetingsDependencies)
        friends(friendsDependencies)
        notifications(notificationsDependencies)
        files(filesDependencies)
    }
}.start(wait)
