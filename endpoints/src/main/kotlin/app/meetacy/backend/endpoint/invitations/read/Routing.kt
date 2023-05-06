package app.meetacy.backend.endpoint.invitations.read

import app.meetacy.backend.endpoint.invitations.InvitationsGetDependencies
import app.meetacy.backend.endpoint.types.Invitation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetInvitationParams(
    val id: String,
)

fun Route.readInvitationRouting(readInvitationRepository: ReadInvitationRepository) {
    get("/read") {
        val invitationParams: GetInvitationParams = call.receive()

        val response = readInvitationRepository.getInvitation(invitationParams)

        val httpStatusCode = when (response) {
            is InvitationsGetResponse.Success -> {
                HttpStatusCode.OK
            }

            InvitationsGetResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }

            InvitationsGetResponse.NotFound -> {
                HttpStatusCode.NotFound
            }
        }

        call.respond(httpStatusCode, if (response is InvitationsGetResponse.Success) response else "")
    }
}

interface ReadInvitationRepository {
    fun getInvitation(getInvitationParams: GetInvitationParams): InvitationsGetResponse
}

sealed interface InvitationsGetResponse {
    data class Success(val response: Invitation): InvitationsGetResponse
    object NoPermissions: InvitationsGetResponse
    object NotFound: InvitationsGetResponse
}