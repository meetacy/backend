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
        object InvitationsNotFound: Result
        object Unauthorized: Result
    }

    suspend fun AccessIdentity.getInvitations(): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        val invitations = with(storage) { userId.getInvitations() }
        return Result.Success(invitations)
    }

    suspend fun AccessIdentity.getInvitations(from: List<UserId>): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }
        with(storage) {
            if (from.any { !it.doesExist() }) return Result.UsersNotFound
        }

        val invitations = with(storage) { userId.getInvitations(from) }
        return Result.Success(invitations)
    }

    suspend fun AccessIdentity.getInvitationsByIds(ids: List<InvitationId>): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }
        with(storage) {
            if (ids.any { !it.doesExist() }) return Result.InvitationsNotFound
        }

        val invitations = with(storage) { userId.getInvitationsByIds(ids) }
        return Result.Success(invitations)
    }

    interface Storage {
        suspend fun UserId.getInvitations(): List<Invitation>
        suspend fun UserId.getInvitations(from: List<UserId>): List<Invitation>
        suspend fun UserId.getInvitationsByIds(ids: List<InvitationId>): List<Invitation>
        suspend fun UserId.doesExist(): Boolean
        suspend fun InvitationId.doesExist(): Boolean
    }
}