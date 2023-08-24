package app.meetacy.backend.feature.meetings.usecase.integration.history.past

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.listMeetingsPastUsecase() {
    val listMeetingsPastUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ListMeetingsPastUsecase.Storage {
            override suspend fun getJoinHistoryFlow(
                userId: UserId,
                startPagingId: PagingId?
            ) = participantsStorage.getJoinHistoryFlow(userId, startPagingId)
        }

        ListMeetingsPastUsecase(
            authRepository = authRepository,
            storage = storage,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}
