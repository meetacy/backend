package app.meetacy.backend.usecase.meetings.participants.list

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

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
        class Success(val paging: PagingResult<UserView>) : Result
        object MeetingNotFound : Result
        object TokenInvalid : Result
    }

    interface Storage {
        suspend fun getMeetingParticipants(
            meetingId: MeetingId,
            amount: Amount,
            pagingId: PagingId?
        ): PagingResult<UserId>
    }
}
