package app.meetacy.backend.application.endpoints.invitations

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.feature.invitations.endpoints.InvitationsDependencies
import app.meetacy.backend.application.usecase.invitations.accept.acceptInvitationRepository
import app.meetacy.backend.application.usecase.invitations.cancel.cancelInvitationRepository
import app.meetacy.backend.application.usecase.invitations.create.createInvitationRepository
import app.meetacy.backend.application.usecase.invitations.deny.denyInvitationRepository

val DI.invitationsDependencies: InvitationsDependencies by Dependency

fun DIBuilder.invitations() {
    acceptInvitationRepository()
    cancelInvitationRepository()
    createInvitationRepository()
    denyInvitationRepository()
    val invitationsDependencies by singleton<InvitationsDependencies> {
        InvitationsDependencies(
            invitationsCreateRepository = createInvitationRepository,
            invitationsAcceptRepository = acceptInvitationRepository,
            invitationsDenyRepository = denyInvitationRepository,
            invitationCancelRepository = cancelInvitationRepository
        )
    }
}
