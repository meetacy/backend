package app.meetacy.backend.usecase.invitations.read

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.authorizeWithUserId

class ReadInvitationUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository
) {
    sealed interface Result {
        data class Success(val invitations: List<FullInvitation>): Result
        object UsersNotFound: Result
        object InvitationsNotFound: Result
        object Unauthorized: Result
    }

    suspend fun getInvitations(token: AccessIdentity): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }

        val invitations = storage.getInvitations(userId)
        return Result.Success(invitations)
    }

    suspend fun getInvitations(from: List<UserId>, token: AccessIdentity): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        if (from.any { storage.getFullUser(it) == null }) return Result.UsersNotFound
        val invitations = storage.getInvitations(from, userId)

        return Result.Success(invitations)
    }

    suspend fun getInvitationsByIds(ids: List<InvitationId>, token: AccessIdentity): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        if (ids.any { storage.getInvitation(it) == null }) return Result.InvitationsNotFound
        val invitations = storage.getInvitationsByIds(ids).filter { it.invitedUserId == userId }

        return Result.Success(invitations)
    }

    interface Storage {
        suspend fun getInvitations(invited: UserId): List<FullInvitation>
        suspend fun getInvitations(from: List<UserId>, to: UserId): List<FullInvitation>
        suspend fun getInvitationsByIds(ids: List<InvitationId>): List<FullInvitation>
        suspend fun getFullUser(id: UserId): FullUser?
        suspend fun getInvitation(id: InvitationId): FullInvitation?
    }
}
