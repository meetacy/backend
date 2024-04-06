package app.meetacy.backend.feature.invitations.usecase.create

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.invitations.usecase.types.getInvitationView
import app.meetacy.backend.types.invitation.InvitationView
import app.meetacy.backend.types.users.*

class CreateInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: AccessHashGenerator,
    private val invitationsRepository: GetInvitationsViewsRepository,
    private val getUsersUsersViewsRepository: GetUsersViewsRepository
) {
    sealed interface Result {
        data class Success(val invitations: List<InvitationView>) : Result
        data object Unauthorized : Result
        data object NoPermissions : Result
        data object UserNotFound : Result
        data object MeetingNotFound : Result
        data object UserAlreadyInvited : Result
    }

    suspend fun createInvitations(
        token: AccessIdentity,
        meetingIdentity: MeetingIdentity,
        usersIdentities: List<UserIdentity>
    ): Result {
        val inviterId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }

        storage.getMeeting(meetingIdentity.id)
            ?.apply { require(identity == meetingIdentity) } ?: return Result.MeetingNotFound

        val usersViews = getUsersUsersViewsRepository.getUsersViewsOrNull(inviterId, usersIdentities)

        for (userView in usersViews) {
            userView ?: return Result.UserNotFound
        }

        val usersIds: Set<UserId> = usersIdentities.map(UserIdentity::id).toSet()

        if (storage.isSubscribers(usersIds.toList(), inviterId).any { !it })
            return Result.NoPermissions

        val alreadyInvitedUsersIds: Set<UserId> = storage.getInvitations(inviterId, meetingIdentity.id)
            .mapNotNull { invitation -> invitation.takeIf { it.invitedUserId in usersIds }?.invitedUserId }
            .toSet()

        val newInvitationsIds = usersIds - alreadyInvitedUsersIds

        val invitations = newInvitationsIds.map { userId ->
            val invitationId = storage.createInvitation(
                accessHash = AccessHash(hashGenerator.generate()),
                invitedUserId = userId,
                inviterUserId = inviterId,
                meetingId = meetingIdentity.id
            )
            storage.addNotification(
                userId = userId,
                inviterId = inviterId,
                meetingId = meetingIdentity.id
            )
            invitationsRepository.getInvitationView(inviterId, invitationId)
        }
        return Result.Success(invitations)
    }

    interface Storage {
        suspend fun isSubscribers(subscribersIds: List<UserId>, userId: UserId): List<Boolean>
        suspend fun getMeeting(meetingId: MeetingId): FullMeeting?
        suspend fun getInvitations(inviterId: UserId, meetingId: MeetingId): List<FullInvitation>
        suspend fun createInvitation(
            accessHash: AccessHash,
            invitedUserId: UserId,
            inviterUserId: UserId,
            meetingId: MeetingId
        ): InvitationId

        suspend fun addNotification(
            userId: UserId,
            inviterId: UserId,
            meetingId: MeetingId
        )
    }
}
