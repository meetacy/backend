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
import app.meetacy.backend.endpoint.meet.create.CreateMeetResult
import app.meetacy.backend.endpoint.meet.get.GetMeeting
import app.meetacy.backend.endpoint.meet.list.GetListMeet
import app.meetacy.backend.endpoint.meet.meetings
import app.meetacy.backend.endpoint.meet.participate.ParticipateMeeting
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
    getListMeet: GetListMeet,
    createMeetResult: CreateMeetResult,
    getMeeting: GetMeeting,
    participateMeeting: ParticipateMeeting

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
        getUser(getUsersProvider)
        meetings(getListMeet, getMeeting, createMeetResult, participateMeeting)

    }
}.start(wait)
