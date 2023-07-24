@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.invitations

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.infrastructure.integrations.invitations.accept.acceptInvitationRepository
import app.meetacy.backend.infrastructure.integrations.invitations.cancel.cancelInvitationRepository
import app.meetacy.backend.infrastructure.integrations.invitations.create.createInvitationRepository
import app.meetacy.backend.infrastructure.integrations.invitations.deny.denyInvitationRepository

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
