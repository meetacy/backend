package app.meetacy.backend.infrastructure.integrations.invitations

import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.infrastructure.integrations.invitations.accept.acceptInvitationRepository
import app.meetacy.backend.infrastructure.integrations.invitations.cancel.cancelInvitationRepository
import app.meetacy.backend.infrastructure.integrations.invitations.create.createInvitationRepository
import app.meetacy.backend.infrastructure.integrations.invitations.deny.denyInvitationRepository
import org.jetbrains.exposed.sql.Database

fun invitationDependenciesFactory(
    db: Database
): InvitationsDependencies = InvitationsDependencies(
    invitationsCreateRepository = createInvitationRepository(db),
    invitationsAcceptRepository = acceptInvitationRepository(db),
    invitationsDenyRepository = denyInvitationRepository(db),
    invitationCancelRepository = cancelInvitationRepository(db)
)
