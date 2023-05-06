package app.meetacy.backend.usecase.invitations.read

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.Invitation
import app.meetacy.backend.usecase.types.authorizeWithUserId

class ReadInvitationUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository
) {
    sealed interface Result {
        data class Success(val invitations: List<Invitation>): Result
        object UsersNotFound: Result
        object InvalidUserId: Result
        object InvitationsNotFound: Result
        object Unauthorized: Result
    }

    suspend fun AccessIdentity.getInvitations(): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        val invitations = with(storage) { userId.getInvitations() }
        return invitations.toResult()
    }

    suspend fun AccessIdentity.getInvitations(from: List<UserId>): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        val invitations = with(storage) { userId.getInvitations(from) }
        return invitations.toResult()
    }

    suspend fun AccessIdentity.getInvitations(ids: List<InvitationId>): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        val invitations = with(storage) { userId.getInvitations(ids) }
        return invitations.toResult()
    }

    interface Storage {
        sealed interface DatabaseResult {
            object UserNotValid: DatabaseResult
            object UsersNotFound: DatabaseResult
            object InvitationsNotFound: DatabaseResult
            data class Success(val invitations: List<Invitation>): DatabaseResult
        }

        suspend fun UserId.getInvitations(): DatabaseResult
        suspend fun UserId.getInvitations(from: List<UserId>): DatabaseResult
        suspend fun UserId.getInvitations(ids: List<InvitationId>): DatabaseResult
    }

    private fun Storage.DatabaseResult.toResult(): Result {
        return when (this) {
            is Storage.DatabaseResult.Success -> Result.Success(this.invitations)
            Storage.DatabaseResult.InvitationsNotFound -> Result.InvitationsNotFound
            Storage.DatabaseResult.UserNotValid -> Result.InvalidUserId
            Storage.DatabaseResult.UsersNotFound -> Result.UsersNotFound
        }
    }
}