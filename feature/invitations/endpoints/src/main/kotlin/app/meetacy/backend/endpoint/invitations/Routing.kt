package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.endpoint.invitations.accept.invitationAccept
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationRepository
import app.meetacy.backend.endpoint.invitations.cancel.invitationCancel
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.invitationCreate
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.invitationDeny
import io.ktor.server.routing.*

class InvitationsDependencies(
    val invitationsCreateRepository: CreateInvitationRepository,
    val invitationsDenyRepository: DenyInvitationRepository,
    val invitationsAcceptRepository: AcceptInvitationRepository,
    val invitationCancelRepository: CancelInvitationRepository
)

fun Route.invitations(
    invitationsDependencies: InvitationsDependencies
) = route("/invitations") {
    invitationCreate(invitationsDependencies.invitationsCreateRepository)
    invitationDeny(invitationsDependencies.invitationsDenyRepository)
    invitationAccept(invitationsDependencies.invitationsAcceptRepository)
    invitationCancel(invitationsDependencies.invitationCancelRepository)
}
