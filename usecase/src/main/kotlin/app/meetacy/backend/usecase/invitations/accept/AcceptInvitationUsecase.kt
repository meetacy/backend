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
    }

    suspend fun AccessIdentity.addToMeetingByInvitation(invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        with(storage) {
            if (userId.isInvited(invitationId) == Storage.DatabaseResult.Success) {
                return userId.addToMeetingByInvitation(invitationId).toUsecase()
            } else return Result.NotFound
        }
    }

    interface Storage {
        sealed interface DatabaseResult {
            object Success: DatabaseResult
            object InvitationExpired: DatabaseResult
            object NotInvited: DatabaseResult
        }

        suspend fun UserId.isInvited(invitationId: InvitationId): DatabaseResult
        suspend fun UserId.addToMeetingByInvitation(invitationId: InvitationId): DatabaseResult
    }

    private fun Storage.DatabaseResult.toUsecase(): Result = when (this) {
        Storage.DatabaseResult.InvitationExpired -> Result.InvitationExpired
        Storage.DatabaseResult.NotInvited -> Result.NotFound
        Storage.DatabaseResult.Success -> Result.Success
    }
}