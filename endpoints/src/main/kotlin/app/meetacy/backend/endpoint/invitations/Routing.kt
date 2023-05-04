package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.invitationAcceptRouting
import app.meetacy.backend.endpoint.invitations.crud.crudInvitationRouting
import io.ktor.server.routing.*

fun Route.invitationsRouting() = route("/invitations") {
    crudInvitationRouting()
    invitationAcceptRouting()
}