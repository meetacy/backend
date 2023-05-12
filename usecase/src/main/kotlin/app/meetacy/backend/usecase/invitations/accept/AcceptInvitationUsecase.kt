package app.meetacy.backend.usecase.invitations.accept

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class AcceptInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object NotFound: Result
        object Unauthorized: Result
        object InvitationExpired: Result
        object MeetingNotFound: Result
    }

    suspend fun AccessIdentity.addToMeetingByInvitation(invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        with(storage) {
            when {
                !userId.isInvited(invitationId) -> return Result.NotFound
                invitationId.isExpired() -> return Result.InvitationExpired
                !invitationId.doesMeetingExist() -> return Result.MeetingNotFound
                else -> {
                    return if (userId.addToMeetingByInvitation(invitationId)) Result.Success else Result.NotFound
                }
            }
        }
    }

    interface Storage {
        suspend fun UserId.isInvited(invitationId: InvitationId): Boolean
        suspend fun UserId.addToMeetingByInvitation(invitationId: InvitationId): Boolean
        suspend fun InvitationId.isExpired(): Boolean
        suspend fun InvitationId.doesMeetingExist(): Boolean
    }
}