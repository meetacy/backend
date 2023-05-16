package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.endpoint.invitations.accept.invitationAccept
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationRepository
import app.meetacy.backend.endpoint.invitations.cancel.invitationCancel
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.invitationCreate
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.invitationDeny
import app.meetacy.backend.endpoint.invitations.read.ReadInvitationRepository
import app.meetacy.backend.endpoint.invitations.read.readInvitation
import app.meetacy.backend.endpoint.invitations.update.InvitationUpdateRepository
import app.meetacy.backend.endpoint.invitations.update.invitationUpdate
import io.ktor.server.routing.*

class InvitationsDependencies(
    val invitationsCreateDependencies: CreateInvitationRepository,
    val invitationsGetDependencies: ReadInvitationRepository,
    val invitationUpdateRepository: InvitationUpdateRepository,
    val invitationsDenyRepository: DenyInvitationRepository,
    val invitationsAcceptRepository: AcceptInvitationRepository,
    val invitationCancelRepository: CancelInvitationRepository
)

fun Route.invitations(
    invitationsDependencies: InvitationsDependencies
) = route("/invitations") {
    invitationCreate(invitationsDependencies.invitationsCreateDependencies)
    readInvitation(invitationsDependencies.invitationsGetDependencies)
    invitationUpdate(invitationsDependencies.invitationUpdateRepository)
    invitationDeny(invitationsDependencies.invitationsDenyRepository)
    invitationAccept(invitationsDependencies.invitationsAcceptRepository)
    invitationCancel(invitationsDependencies.invitationCancelRepository)
}