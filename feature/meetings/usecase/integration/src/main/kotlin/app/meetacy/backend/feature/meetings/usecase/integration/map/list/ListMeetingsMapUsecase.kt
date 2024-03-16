package app.meetacy.backend.feature.meetings.usecase.integration.map.list

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.SortOrder

internal fun DIBuilder.listMeetingsMapUsecase() {
    val listMeetingsMapUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting
        val viewMeetingsRepository: ViewMeetingsRepository by getting

        val participantsStorage: ParticipantsStorage by getting
        val meetingsStorage: MeetingsStorage by getting

        val storage = object : ListMeetingsMapUsecase.Storage {
            override suspend fun getMeetingsHistoryFlowAscending(userId: UserId, amount: Amount, afterDate: Date): List<MeetingId> {
                return participantsStorage.getJoinHistoryFlow(
                    userId,
                    amount = amount,
                    pagingId = null,
                    sortOrder = SortOrder.ASC,
                    afterDate = afterDate
                ).map { it.value }
            }
            override suspend fun getPublicMeetingsFlow(): Flow<FullMeeting> {
                return meetingsStorage.getPublicMeetingsFlow()
            }
        }

        ListMeetingsMapUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            viewMeetingsRepository = viewMeetingsRepository,
            storage = storage
        )
    }
}
