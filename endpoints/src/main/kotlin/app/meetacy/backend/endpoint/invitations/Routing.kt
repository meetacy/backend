package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.invitationAcceptRouting
import app.meetacy.backend.endpoint.invitations.create.createInvitationRouting
import app.meetacy.backend.endpoint.invitations.delete.invitationDeleteRouting
import app.meetacy.backend.endpoint.invitations.read.getInvitationRouting
import app.meetacy.backend.endpoint.invitations.update.invitationUpdateRouting
import io.ktor.server.routing.*

fun Route.invitationsRouting() = route("/invitations") {
    createInvitationRouting()
    getInvitationRouting()
    invitationUpdateRouting()
    invitationDeleteRouting()
    invitationAcceptRouting()
}