package app.meetacy.backend.feature.invitations.endpoints.integration

import app.meetacy.backend.feature.invitations.endpoints.integration.accept.invitationAccept
import app.meetacy.backend.feature.invitations.endpoints.integration.cancel.invitationCancel
import app.meetacy.backend.feature.invitations.endpoints.integration.create.invitationCreate
import app.meetacy.backend.feature.invitations.endpoints.integration.deny.invitationDeny
import io.ktor.server.routing.*

fun Route.invitations() = route("/invitations") {
    invitationAccept()
    invitationCancel()
    invitationCreate()
    invitationDeny()
}
