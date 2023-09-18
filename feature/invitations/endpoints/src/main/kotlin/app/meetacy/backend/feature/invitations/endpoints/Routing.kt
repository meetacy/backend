package app.meetacy.backend.feature.invitations.endpoints

import app.meetacy.backend.feature.invitations.endpoints.accept.AcceptInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.accept.invitationAccept
import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.cancel.invitationCancel
import app.meetacy.backend.feature.invitations.endpoints.create.CreateInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.create.invitationCreate
import app.meetacy.backend.feature.invitations.endpoints.deny.DenyInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.deny.invitationDeny
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
