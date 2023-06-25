package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*
import org.jetbrains.exposed.sql.Database

class DatabaseGetInvitationsViewsRepository(
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val getMeetingsViewsRepository: GetMeetingsViewsRepository,
    db: Database
): GetInvitationsViewsRepository {
    private val invitationsStorage = InvitationsStorage(db)

    override suspend fun getInvitationViewOrNull(viewerId: UserId, invitation: FullInvitation): InvitationView? {
        with(invitation) {
            val invitedUserView = getUsersViewsRepository.getUserViewOrNull(viewerId, invitedUserId) ?: return null
            val invitorUserView = getUsersViewsRepository.getUserViewOrNull(viewerId, invitorUserId) ?: return null
            val meetingView = getMeetingsViewsRepository.getMeetingViewOrNull(viewerId, meeting) ?: return null

            return InvitationView(identity, expiryDate, invitedUserView, invitorUserView, meetingView, isAccepted)
        }
    }

    override suspend fun getInvitationViewOrNull(viewerId: UserId, invitation: InvitationId): InvitationView? {
        val fullInvitation = invitationsStorage.getInvitationsByInvitationIds(listOf(invitation))
            .singleOrNull()?.mapToUsecase() ?: return null

        return getInvitationViewOrNull(viewerId, fullInvitation)
    }

    override suspend fun getInvitationView(viewerId: UserId, invitation: FullInvitation): InvitationView {
        with(invitation) {
            val invitedUserView = getUsersViewsRepository.getUserView(viewerId, invitedUserId)
            val invitorUserView = getUsersViewsRepository.getUserView(viewerId, invitorUserId)
            val meetingView = getMeetingsViewsRepository.getMeetingsViews(viewerId, listOf(meeting)).single()

            return InvitationView(identity, expiryDate, invitedUserView, invitorUserView, meetingView, isAccepted)
        }
    }

    override suspend fun getInvitationView(viewerId: UserId, invitation: InvitationId): InvitationView {
        val fullInvitation = invitationsStorage.getInvitationsByInvitationIds(listOf(invitation)).apply {
            require(size == 1) { "Couldn't find invitation. Consider use getInvitationViewOrNull instead" }
        }.single().mapToUsecase()

        return getInvitationView(viewerId, fullInvitation)
    }
}
