package app.meetacy.backend.feature.invitations.endpoints.integration

import app.meetacy.backend.feature.invitations.endpoints.integration.accept.invitationAccept
import app.meetacy.backend.feature.invitations.endpoints.integration.cancel.invitationCancel
import app.meetacy.backend.feature.invitations.endpoints.integration.create.invitationCreate
import app.meetacy.backend.feature.invitations.endpoints.integration.deny.invitationDeny
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.invitations(di: DI) = route("/invitations") {
    invitationAccept(di)
    invitationCancel(di)
    invitationCreate(di)
    invitationDeny(di)
}
