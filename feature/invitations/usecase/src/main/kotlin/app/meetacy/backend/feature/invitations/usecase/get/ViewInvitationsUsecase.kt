package app.meetacy.backend.feature.invitations.usecase.get

import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.getMeetingsViews
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.getUsersViews
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.InvitationView

class ViewInvitationsUsecase(
    private val usersRepository: GetUsersViewsRepository,
    private val meetingsRepository: GetMeetingsViewsRepository
) {
    suspend fun viewInvitations(
        viewerId: UserId,
        invitations: List<FullInvitation>
    ): List<InvitationView> {
        val users = invitations.flatMap { invitation ->
            listOf(invitation.invitedUserId, invitation.inviterUserId)
        }
            .let { ids -> usersRepository.getUsersViews(viewerId, ids) }
            .iterator()

        val meetings = invitations.map { invitation ->
            invitation.meetingId
        }
            .let { ids -> meetingsRepository.getMeetingsViews(viewerId, ids) }
            .iterator()

        return invitations.map { invitation ->
            with (invitation) {
                InvitationView(
                    id = id,
                    invitedUser = users.next(),
                    inviterUser = users.next(),
                    meeting = meetings.next(),
                    isAccepted = isAccepted
                )
            }
        }
    }
}
