package app.meetacy.backend.feature.meetings.usecase.integration.view

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.get.ViewMeetingsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.viewMeetingsUsecase() {
    val viewMeetingsUsecase by singleton<ViewMeetingsUsecase> {
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val filesRepository: FilesRepository by getting
        val storage = object : ViewMeetingsUsecase.Storage {
            val participantsStorage: ParticipantsStorage by getting

            override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
                participantsStorage.participantsCount(meetingIds)

            override suspend fun getIsParticipates(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
               participantsStorage.isParticipating(meetingIds, viewerId)

            override suspend fun getFirstParticipants(limit: Amount, meetingIds: List<MeetingId>): List<List<UserId>> =
                participantsStorage.getFirstParticipants(meetingIds, limit).map { it.data }

        }
        ViewMeetingsUsecase(getUsersViewsRepository, filesRepository, storage)
    }
}
