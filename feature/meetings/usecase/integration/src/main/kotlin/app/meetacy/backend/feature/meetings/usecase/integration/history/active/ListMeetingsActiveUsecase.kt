package app.meetacy.backend.feature.meetings.usecase.integration.history.active

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow

internal fun DIBuilder.listMeetingsActiveUsecase() {
    val listMeetingsActiveUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ListMeetingsActiveUsecase.Storage {
            override suspend fun getJoinHistoryFlow(
                userId: UserId,
                startPagingId: PagingId?
            ): Flow<PagingValue<MeetingId>> =
                participantsStorage.getJoinHistoryFlow(userId, startPagingId)
        }

        ListMeetingsActiveUsecase(
            authRepository = authRepository,
            storage = storage,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}
