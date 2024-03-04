package app.meetacy.backend.application.endpoints

import app.meetacy.backend.application.endpoints.deeplink.deeplinks
import app.meetacy.backend.database.initDatabase
import app.meetacy.backend.endpoint.ktor.exceptions.installExceptionsHandler
import app.meetacy.backend.endpoint.ktor.rsocket.installRSocket
import app.meetacy.backend.endpoint.ktor.versioning.ApiVersion
import app.meetacy.backend.feature.auth.endpoints.integration.auth
import app.meetacy.backend.feature.files.endpoints.integration.files
import app.meetacy.backend.feature.friends.endpoints.integration.friends
import app.meetacy.backend.feature.invitations.endpoints.integration.invitations
import app.meetacy.backend.feature.meetings.endpoints.integration.meetings
import app.meetacy.backend.feature.notifications.endpoints.integration.notifications
import app.meetacy.backend.feature.search.endpoints.integration.search
import app.meetacy.backend.feature.updates.endpoints.integration.updates
import app.meetacy.backend.feature.users.endpoints.integration.users
import app.meetacy.di.DI
import io.ktor.http.HttpHeaders
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.partialcontent.PartialContent
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.exposed.sql.Database

@Suppress("ExtractKtorModule")
suspend fun prepareEndpoints(di: DI): ApplicationEngine {
    val port: Int by di.getting
    val database: Database by di.getting
    val exceptionHandler: ExceptionHandler by di.getting
    val coroutineScope: CoroutineScope by di.getting

    initDatabase(database)

    return coroutineScope.embeddedServer(CIO, host = "localhost", port = port) {
        installJson()
        install(CORS) {
            allowCredentials = true
            anyHost()
            allowHeader(HttpHeaders.ContentType)
            allowHeader(ApiVersion.Header)
        }
        install(PartialContent)
        install(AutoHeadResponse)
        install(DoubleReceive)
        installExceptionsHandler(exceptionHandler.map())
        installRSocket()

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
            search(di)
            updates(di)
            users(di)

            deeplinks()

            get("/duntsova") {
                call.respondRedirect("https://дунцова.рф")
            }
        }
    }
}
