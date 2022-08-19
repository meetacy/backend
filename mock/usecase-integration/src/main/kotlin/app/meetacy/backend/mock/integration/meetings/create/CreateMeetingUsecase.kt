package app.meetacy.backend.mock.integration.meetings.create

import app.meetacy.backend.mock.integration.meetings.participate.MockViewMeetingsUsecaseStorage
import app.meetacy.backend.mock.integration.types.MockGetUsersViewsRepository
import app.meetacy.backend.mock.integration.types.mapToUsecase
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.Date
import app.meetacy.backend.types.Location
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.MeetingsStorage
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView

object MockCreateMeetingStorage : CreateMeetingUsecase.Storage {
    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): FullMeeting =
        MeetingsStorage
            .addMeeting(accessHash, creatorId, date, location, title, description)
            .mapToUsecase()
}

object MockCreateMeetingViewMeetingRepository : CreateMeetingUsecase.ViewMeetingRepository {
    override suspend fun viewMeeting(
        viewer: UserId,
        meeting: FullMeeting
    ): MeetingView = ViewMeetingsUsecase(MockGetUsersViewsRepository, MockViewMeetingsUsecaseStorage)
        .viewMeetings(viewer, meetings = listOf(meeting)).first()
}
