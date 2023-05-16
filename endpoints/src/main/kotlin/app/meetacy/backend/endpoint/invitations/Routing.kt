package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.endpoint.invitations.accept.invitationAcceptRouting
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationRepository
import app.meetacy.backend.endpoint.invitations.cancel.invitationCancelRouting
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.createInvitation
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.invitationDeny
import app.meetacy.backend.endpoint.invitations.read.ReadInvitationRepository
import app.meetacy.backend.endpoint.invitations.read.readInvitation
import app.meetacy.backend.endpoint.invitations.update.InvitationUpdateRepository
import app.meetacy.backend.endpoint.invitations.update.invitationUpdateRouting
import io.ktor.server.routing.*

class InvitationsDependencies(
    val invitationsCreateDependencies: CreateInvitationRepository,
    val invitationsGetDependencies: ReadInvitationRepository,
    val invitationUpdateRepository: InvitationUpdateRepository,
    val invitationsDenyRepository: DenyInvitationRepository,
    val invitationsAcceptRepository: AcceptInvitationRepository,
    val invitationCancelRepository: CancelInvitationRepository?
)

fun Route.invitations(
    invitationsDependencies: InvitationsDependencies
) = route("/invitations") {
    createInvitation(invitationsDependencies.invitationsCreateDependencies)
    readInvitation(invitationsDependencies.invitationsGetDependencies)
    invitationUpdateRouting(invitationsDependencies.invitationUpdateRepository)
    invitationDeny(invitationsDependencies.invitationsDenyRepository)
    invitationAcceptRouting(invitationsDependencies.invitationsAcceptRepository)
    invitationCancelRouting(invitationsDependencies.invitationCancelRepository)
}