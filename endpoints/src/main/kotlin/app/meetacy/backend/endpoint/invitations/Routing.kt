package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.endpoint.invitations.accept.invitationAcceptRouting
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.createInvitationRouting
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.invitationDenyRouting
import app.meetacy.backend.endpoint.invitations.read.ReadInvitationRepository
import app.meetacy.backend.endpoint.invitations.read.readInvitationRouting
import app.meetacy.backend.endpoint.invitations.update.InvitationUpdateRepository
import app.meetacy.backend.endpoint.invitations.update.invitationUpdateRouting
import io.ktor.server.routing.*

class InvitationsDependencies(
    val invitationsCreateDependencies: CreateInvitationRepository,
    val invitationsGetDependencies: ReadInvitationRepository,
    val invitationUpdateRepository: InvitationUpdateRepository,
    val invitationsDenyRepository: DenyInvitationRepository,
    val invitationsAcceptDependencies: AcceptInvitationRepository?
)

fun Route.invitations(
    invitationsDependencies: InvitationsDependencies
) = route("/invitations") {
    createInvitationRouting(invitationsDependencies.invitationsCreateDependencies)
    readInvitationRouting(invitationsDependencies.invitationsGetDependencies)
    invitationUpdateRouting(invitationsDependencies.invitationUpdateRepository)
    invitationDenyRouting(invitationsDependencies.invitationsDenyRepository)
    invitationAcceptRouting(invitationsDependencies.invitationsAcceptDependencies)
}