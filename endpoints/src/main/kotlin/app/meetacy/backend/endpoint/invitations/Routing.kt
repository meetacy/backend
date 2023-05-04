package app.meetacy.backend.endpoint.invitations

import app.meetacy.backend.endpoint.invitations.accept.invitationAcceptRouting
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.createInvitationRouting
import app.meetacy.backend.endpoint.invitations.delete.invitationDeleteRouting
import app.meetacy.backend.endpoint.invitations.read.getInvitationRouting
import app.meetacy.backend.endpoint.invitations.update.invitationUpdateRouting
import io.ktor.server.routing.*

class InvitationsDependencies(
    val invitationsCreateDependencies: CreateInvitationRepository,
    val invitationsGetDependencies: InvitationsGetDependencies?,
    val invitationsUpdateDependencies: InvitationsUpdateDependencies?,
    val invitationsDeleteDependencies: InvitationsDeletionDependencies?,
    val invitationsAcceptDependencies: InvitationsAcceptationDependencies?
)

class InvitationsAcceptationDependencies

class InvitationsDeletionDependencies

class InvitationsUpdateDependencies

class InvitationsGetDependencies

fun Route.invitations(
    invitationsDependencies: InvitationsDependencies
) = route("/invitations") {
    createInvitationRouting(invitationsDependencies.invitationsCreateDependencies)
    getInvitationRouting(invitationsDependencies.invitationsGetDependencies)
    invitationUpdateRouting(invitationsDependencies.invitationsUpdateDependencies)
    invitationDeleteRouting(invitationsDependencies.invitationsDeleteDependencies)
    invitationAcceptRouting(invitationsDependencies.invitationsAcceptDependencies)
}