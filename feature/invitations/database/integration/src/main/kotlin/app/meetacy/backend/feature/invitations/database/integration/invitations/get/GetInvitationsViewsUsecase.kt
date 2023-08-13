package app.meetacy.backend.feature.invitations.database.integration.invitations.get

import app.meetacy.backend.feature.invitations.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.feature.invitations.usecase.invitations.get.GetInvitationsViewsUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.ViewInvitationsRepository
import org.jetbrains.exposed.sql.Database

fun GetInvitationsViewsUsecase(
    db: Database,
    viewInvitationsRepository: ViewInvitationsRepository
): GetInvitationsViewsUsecase {
    return GetInvitationsViewsUsecase(
        viewInvitationsRepository = viewInvitationsRepository,
        invitationsProvider = DatabaseGetInvitationsViewsUsecaseInvitationsProvider(db)
    )
}

class DatabaseGetInvitationsViewsUsecaseInvitationsProvider(
    db: Database
) : GetInvitationsViewsUsecase.InvitationsProvider {
    private val invitationsStorage = InvitationsStorage(db)

    override suspend fun getInvitationsOrNull(invitationIds: List<InvitationId>): List<FullInvitation?> {
        return invitationsStorage
            .getInvitationsOrNull(invitationIds)
            .map { it?.mapToUsecase() }
    }
}
