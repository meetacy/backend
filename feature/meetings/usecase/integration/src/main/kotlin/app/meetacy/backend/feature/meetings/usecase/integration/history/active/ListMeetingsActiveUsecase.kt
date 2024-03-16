package app.meetacy.backend.feature.meetings.usecase.integration.history.active

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.SortOrder

internal fun DIBuilder.listMeetingsActiveUsecase() {
    val listMeetingsActiveUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ListMeetingsActiveUsecase.Storage {
            override suspend fun getJoinHistoryFlowAscending(
                userId: UserId,
                startPagingId: PagingId?,
                after: Date,
                amount: Amount
            ): List<PagingValue<MeetingId>> {
                return participantsStorage.getJoinHistoryFlow(
                    userId = userId,
                    pagingId = startPagingId,
                    amount = amount,
                    afterDate = after,
                    sortOrder = SortOrder.ASC
                )
            }
        }

        ListMeetingsActiveUsecase(
            authRepository = authRepository,
            storage = storage,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}
