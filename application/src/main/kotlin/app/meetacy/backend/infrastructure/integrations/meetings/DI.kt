@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.create.createMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.delete.deleteMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.edit.editMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.history.meetingsHistoryDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.map.meetingsMapDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.participate.participantDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.participate.participateMeetingRepository

val DI.meetingsDependencies: MeetingsDependencies by Dependency

fun DIBuilder.meetings() {
    checkMeetingsRepository()
    createMeetingRepository()
    deleteMeetingRepository()
    editMeetingRepository()
    getMeetingRepository()
    meetingsHistoryDependencies()
    meetingsMapDependencies()
    participateMeetingRepository()
    val meetingsDependencies by singleton<MeetingsDependencies> {
        MeetingsDependencies(
            meetingsHistoryDependencies = meetingsHistoryDependencies,
            meetingsMapDependencies = meetingsMapDependencies,
            meetingParticipantsDependencies = participantDependencies,
            createMeetingRepository = createMeetingRepository,
            getMeetingRepository = getMeetingRepository,
            participateMeetingRepository = participateMeetingRepository,
            deleteMeetingRepository = deleteMeetingRepository,
            editMeetingRepository = editMeetingRepository
        )
    }
}
