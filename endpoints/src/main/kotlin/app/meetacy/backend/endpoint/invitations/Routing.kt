package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptRepository
import app.meetacy.backend.endpoint.invitations.accept.invitationAcceptRouting
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.createInvitationRouting
import app.meetacy.backend.endpoint.invitations.delete.invitationDeleteRouting
import app.meetacy.backend.endpoint.invitations.read.ReadInvitationRepository
import app.meetacy.backend.endpoint.invitations.read.readInvitationRouting
import app.meetacy.backend.endpoint.invitations.update.invitationUpdateRouting
import io.ktor.server.routing.*

class InvitationsDependencies(
    val invitationsCreateDependencies: CreateInvitationRepository,
    val invitationsGetDependencies: ReadInvitationRepository,
    val invitationsUpdateDependencies: InvitationsUpdateDependencies?,
    val invitationsDeleteDependencies: InvitationsDeletionDependencies?,
    val invitationsAcceptDependencies: InvitationAcceptRepository?
)

class InvitationsDeletionDependencies

class InvitationsUpdateDependencies

fun Route.invitations(
    invitationsDependencies: InvitationsDependencies
) = route("/invitations") {
    createInvitationRouting(invitationsDependencies.invitationsCreateDependencies)
    readInvitationRouting(invitationsDependencies.invitationsGetDependencies)
    invitationUpdateRouting(invitationsDependencies.invitationsUpdateDependencies)
    invitationDeleteRouting(invitationsDependencies.invitationsDeleteDependencies)
    invitationAcceptRouting(invitationsDependencies.invitationsAcceptDependencies)
}