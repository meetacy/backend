package app.meetacy.backend.feature.meetings.usecase.integration.participants.list

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.MeetingExistsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.listMeetingParticipantsUsecase() {
    val listMeetingParticipantsUsecase by singleton {
        val authRepository: AuthRepository by getting
        val meetingExistsRepository: MeetingExistsRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ListMeetingParticipantsUsecase.Storage {
            override suspend fun getMeetingParticipants(
                meetingId: MeetingId,
                amount: Amount,
                pagingId: PagingId?
            ): PagingResult<UserId> {
                return participantsStorage.getParticipants(meetingId, amount, pagingId)
            }
        }

        ListMeetingParticipantsUsecase(
            authRepository = authRepository,
            meetingExistsRepository = meetingExistsRepository,
            storage = storage,
            getUsersViewsRepository = getUsersViewsRepository
        )
    }
}
