package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.Location
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.MeetingsStorage
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView

private object MockCreateMeetingUsecaseStorage : CreateMeetingUsecase.Storage {
    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): FullMeeting {
        val meeting = MeetingsStorage.addMeeting(accessHash, creatorId, date, location, title, description)
        return FullMeeting(
            meeting.id,
            meeting.accessHash,
            meeting.creatorId,
            meeting.date,
            meeting.location,
            meeting.title,
            meeting.description
        )
    }
}

private object MockViewMeetingRepository : CreateMeetingUsecase.ViewMeetingRepository {
    override suspend fun viewMeeting(
        viewer: UserId,
        meeting: FullMeeting
    ): MeetingView = mockViewMeetingsUsecase().viewMeetings(viewer, meetings = listOf(meeting)).first()
}

fun mockCreateMeetingUsecase(): CreateMeetingUsecase = CreateMeetingUsecase(
    hashGenerator = MockHashGeneratorIntegration,
    storage = MockCreateMeetingUsecaseStorage,
    authRepository = MockAuthRepository,
    viewMeetingRepository = MockViewMeetingRepository
)
