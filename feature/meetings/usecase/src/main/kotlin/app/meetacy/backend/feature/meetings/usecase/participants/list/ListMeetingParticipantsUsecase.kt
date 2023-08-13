package app.meetacy.backend.feature.meetings.usecase.participants.list

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meetings.CheckMeetingRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.meetings.checkMeetingIdentity
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.types.users.getUsersViews

class ListMeetingParticipantsUsecase(
    private val authRepository: AuthRepository,
    private val checkMeetingRepository: CheckMeetingRepository,
    private val storage: Storage,
    private val getUsersViewsRepository: GetUsersViewsRepository
) {

    suspend fun getMeetingParticipants(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) {
            return Result.TokenInvalid
        }

        val meetingId = checkMeetingRepository.checkMeetingIdentity(meetingIdentity) {
            return Result.MeetingNotFound
        }

        val paging = storage.getMeetingParticipants(
            meetingId = meetingId,
            amount = amount,
            pagingId = pagingId
        ).map { users ->
            getUsersViewsRepository.getUsersViews(
                viewerId = userId,
                userIdentities = users
            )
        }

        return Result.Success(paging)
    }

    sealed interface Result {
        data class Success(val paging: PagingResult<UserView>) : Result
        data object MeetingNotFound : Result
        data object TokenInvalid : Result
    }

    interface Storage {
        suspend fun getMeetingParticipants(
            meetingId: MeetingId,
            amount: Amount,
            pagingId: PagingId?
        ): PagingResult<UserId>
    }
}
