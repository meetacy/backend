package app.meetacy.backend.endpoint

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import app.meetacy.backend.endpoint.auth.auth
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailRepository
import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.endpoint.meetings.meetings
import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.friends
import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.notifications.notifications
import app.meetacy.backend.endpoint.users.UserRepository
import app.meetacy.backend.endpoint.users.getUser
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun startEndpoints(
    port: Int,
    wait: Boolean,
    linkEmailRepository: LinkEmailRepository,
    confirmEmailRepository: ConfirmEmailRepository,
    tokenGenerateRepository: TokenGenerateRepository,
    userRepository: UserRepository,
    addFriendRepository: AddFriendRepository,
    getFriendsRepository: GetFriendsRepository,
    meetingsDependencies: MeetingsDependencies,
    notificationsDependencies: NotificationsDependencies
) = embeddedServer(CIO, port) {
    install(ContentNegotiation) {
        json(
            Json {
                explicitNulls = false
            }
        )
    }

    routing {
        auth(linkEmailRepository, confirmEmailRepository, tokenGenerateRepository)
        getUser(userRepository)
        meetings(meetingsDependencies)
        friends(addFriendRepository, getFriendsRepository)
        notifications(notificationsDependencies)
    }
}.start(wait)
