package app.meetacy.backend.feature.meetings.usecase.integration.history.list

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.feature.meetings.usecase.history.list.ListMeetingsHistoryUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow

internal fun DIBuilder.listMeetingsHistoryUsecase() {
    val listMeetingsHistoryUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ListMeetingsHistoryUsecase.Storage {
            override suspend fun getParticipatingMeetings(
                memberId: UserId,
                amount: Amount,
                pagingId: PagingId?
            ) = participantsStorage.getJoinHistory(memberId, amount, pagingId)
        }

        ListMeetingsHistoryUsecase(
            authRepository = authRepository,
            storage = storage,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}
