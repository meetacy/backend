package app.meetacy.backend.infrastructure.integration.meetings

import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.infrastructure.integration.meetings.create.createMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.delete.deleteMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.edit.editMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.get.getMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.history.meetingsHistoryDependencies
import app.meetacy.backend.infrastructure.integration.meetings.map.listMeetingsMapRepository
import app.meetacy.backend.infrastructure.integration.meetings.participate.participantDependencies
import app.meetacy.backend.infrastructure.integration.meetings.participate.participateMeetingRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.meetingsDependencies: MeetingsDependencies by Dependency

fun DIBuilder.meetings() {
    createMeetingRepository()
    deleteMeetingRepository()
    editMeetingRepository()
    getMeetingRepository()
    meetingsHistoryDependencies()
    listMeetingsMapRepository()
    participateMeetingRepository()
    val meetingsDependencies by singleton<MeetingsDependencies> {
        MeetingsDependencies(
            meetingParticipantsDependencies = participantDependencies,
            getMeetingRepository = getMeetingRepository,
            createMeetingRepository = createMeetingRepository,
            participateMeetingRepository = participateMeetingRepository,
            deleteMeetingRepository = deleteMeetingRepository,
            editMeetingRepository = editMeetingRepository
        )
    }
}
