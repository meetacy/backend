package app.meetacy.backend.database.integration.invitations.get

import app.meetacy.backend.feature.auth.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.usecase.invitations.get.GetInvitationsViewsUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.ViewInvitationsRepository
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
