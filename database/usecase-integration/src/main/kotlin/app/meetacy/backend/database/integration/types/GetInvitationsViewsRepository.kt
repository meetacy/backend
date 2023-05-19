package app.meetacy.backend.database.integration.types

import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

class DatabaseGetInvitationsViewsRepository(
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository
): GetInvitationsViewsRepository {
    override suspend fun getInvitationView(viewerId: UserId, invitation: FullInvitation): InvitationView? {
        with(invitation) {
            val invitedUserView = getUsersViewsRepository.getUserViewOrNull(viewerId, invitedUserId) ?: return null
            val invitorUserView = getUsersViewsRepository.getUserViewOrNull(viewerId, invitorUserId) ?: return null
            val meetingView = getMeetingsViewsRepository.getMeetingViewOrNull(viewerId, meeting) ?: return null

            return InvitationView(identity, expiryDate, invitedUserView, invitorUserView, meetingView, isAccepted)
        }
    }
}