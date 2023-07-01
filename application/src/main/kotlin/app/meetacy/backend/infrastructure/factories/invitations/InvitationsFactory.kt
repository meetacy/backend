package app.meetacy.backend.infrastructure.factories.invitations

import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.infrastructure.factories.invitations.accept.acceptInvitationRepository
import app.meetacy.backend.infrastructure.factories.invitations.cancel.cancelInvitationRepository
import app.meetacy.backend.infrastructure.factories.invitations.create.createInvitationRepository
import app.meetacy.backend.infrastructure.factories.invitations.deny.denyInvitationRepository
import org.jetbrains.exposed.sql.Database

fun invitationDependenciesFactory(
    db: Database
): InvitationsDependencies = InvitationsDependencies(
    invitationsCreateRepository = createInvitationRepository(db),
    invitationsAcceptRepository = acceptInvitationRepository(db),
    invitationsDenyRepository = denyInvitationRepository(db),
    invitationCancelRepository = cancelInvitationRepository(db)
)
