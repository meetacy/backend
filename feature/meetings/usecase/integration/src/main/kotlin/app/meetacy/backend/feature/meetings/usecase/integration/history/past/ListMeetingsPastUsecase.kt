package app.meetacy.backend.feature.meetings.usecase.integration.history.past

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.SortOrder

internal fun DIBuilder.listMeetingsPastUsecase() {
    val listMeetingsPastUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ListMeetingsPastUsecase.Storage {
            override suspend fun getJoinHistoryFlowDescending(
                userId: UserId,
                amount: Amount,
                startPagingId: PagingId?,
                before: Date
            ): List<PagingValue<MeetingId>> {
                return participantsStorage.getJoinHistoryFlow(
                    userId = userId,
                    pagingId = startPagingId,
                    amount = amount,
                    beforeDate = before,
                    sortOrder = SortOrder.DESC
                )
            }
        }

        ListMeetingsPastUsecase(
            authRepository = authRepository,
            storage = storage,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}
