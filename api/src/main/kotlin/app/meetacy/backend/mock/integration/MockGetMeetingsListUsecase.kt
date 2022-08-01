package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.ParticipantsStorage
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase

private object MockGetMeetingsListStorage : GetMeetingsListUsecase.Storage {
    override fun getMeetingsList(memberId: UserId): List<MeetingId> =
        ParticipantsStorage.getMeetingIds(memberId)
}

fun mockGetMeetingsListUsecase(): GetMeetingsListUsecase =
    GetMeetingsListUsecase(
        authRepository = MockAuthRepository,
        storage = MockGetMeetingsListStorage,
        getMeetingsViewsRepository = MockGetMeetingsViewsRepository
    )
