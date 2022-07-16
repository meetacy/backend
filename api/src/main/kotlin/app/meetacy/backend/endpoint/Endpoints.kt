package app.meetacy.backend.endpoint

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import app.meetacy.backend.endpoint.auth.auth
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailStorage
import app.meetacy.backend.endpoint.auth.email.link.Mailer
import app.meetacy.backend.endpoint.auth.generate.TokenGenerator
import app.meetacy.backend.endpoint.friends.addNew.AddFriendsProvider
import app.meetacy.backend.endpoint.friends.friends
import app.meetacy.backend.endpoint.friends.get.GetFriendsProvider
import app.meetacy.backend.endpoint.users.UserProvider
import app.meetacy.backend.endpoint.users.getUser
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun startEndpoints(
    port: Int,
    wait: Boolean,
    mailer: Mailer,
    linkEmailStorage: LinkEmailStorage,
    confirmStorage: ConfirmStorage,
    tokenGenerator: TokenGenerator,
    getUsersProvider: UserProvider,
    provider: AddFriendsProvider,
    getProvider: GetFriendsProvider
) = embeddedServer(CIO, port) {
    install(ContentNegotiation) {
        json(
            Json {
                explicitNulls = false
            }
        )
    }

    routing {
        auth(mailer, linkEmailStorage, confirmStorage, tokenGenerator)
        friends(provider, getProvider)
        getUser(getUsersProvider)
    }
}.start(wait)
