package app.meetacy.backend.usecase.invitations.create

import app.meetacy.backend.types.DESCRIPTION_MAX_LIMIT
import app.meetacy.backend.types.TITLE_MAX_LIMIT
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.types.authorizeWithUserId

class CreateInvitationUsecase (
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: HashGenerator
) {
    sealed interface Result {
        data class Success(val invitation: InvitationId): Result
        object Unauthorized: Result
        object NoPermissions: Result
        object UserNotFound: Result
        object MeetingNotFound: Result
        object UserAlreadyInvited: Result
        object InvalidData: Result
    }

    suspend fun createInvitation(
        token: AccessIdentity,
        title: String,
        description: String,
        expiryDate: DateTime,
        meetingId: MeetingId,
        invitedUserId: UserId
    ): Result {
        val invitorId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }

        with(storage) {
            when {
                !meetingId.doesExist() -> return Result.MeetingNotFound
                !(invitedUserId.doesExist()) -> return Result.UserNotFound

                !(invitedUserId.isSubscriberOf(invitorId) ||
                        invitorId.isFriend(invitedUserId)) -> return Result.NoPermissions

                (expiryDate < DateTime.now() ||
                        title.length > TITLE_MAX_LIMIT ||
                        description.length > DESCRIPTION_MAX_LIMIT) -> return Result.InvalidData

                else -> return Result.Success(
                    invitation = createInvitation(
                        AccessHash(hashGenerator.generate()),
                        invitedUserId,
                        invitorId,
                        title,
                        description,
                        expiryDate,
                        meetingId
                    ) ?: return Result.UserAlreadyInvited
                )
            }
        }
    }

    interface Storage {
        suspend fun UserId.isFriend(user: UserId): Boolean
        suspend fun UserId.isSubscriberOf(subscriberId: UserId): Boolean
        suspend fun MeetingId.doesExist(): Boolean
        suspend fun UserId.doesExist(): Boolean
        suspend fun createInvitation(
            accessHash: AccessHash,
            invitedUserId: UserId,
            invitorUserId: UserId,
            title: String,
            description: String,
            expiryDate: DateTime,
            meetingId: MeetingId
        ): InvitationId?
    }
}