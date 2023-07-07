package app.meetacy.backend.database.integration.types

import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.get.ViewInvitationsUsecase
import app.meetacy.backend.usecase.types.*

fun ViewInvitationsRepository(
    usersRepository: GetUsersViewsRepository,
    meetingsRepository: GetMeetingsViewsRepository
): ViewInvitationsRepository {
    return UsecaseViewInvitationsRepository(
        usecase = ViewInvitationsUsecase(usersRepository, meetingsRepository)
    )
}

class UsecaseViewInvitationsRepository(
    private val usecase: ViewInvitationsUsecase
) : ViewInvitationsRepository {
    override suspend fun viewInvitations(
        viewerId: UserId,
        invitations: List<FullInvitation>
    ): List<InvitationView> {
        return usecase.viewInvitations(viewerId, invitations)
    }
}
