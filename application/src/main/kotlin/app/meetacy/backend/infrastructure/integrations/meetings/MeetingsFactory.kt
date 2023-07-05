package app.meetacy.backend.infrastructure.integrations.meetings

import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.create.createMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.delete.deleteMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.edit.editMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.history.meetingHistoryDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.map.meetingsMapDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.participate.participantDependenciesBuilder
import app.meetacy.backend.infrastructure.integrations.meetings.participate.participateMeetingRepository
import org.jetbrains.exposed.sql.Database

fun meetingsDependenciesFactory(
    db: Database
): MeetingsDependencies = MeetingsDependencies(
    meetingsHistoryDependencies = meetingHistoryDependencies(db),
    meetingsMapDependencies = meetingsMapDependencies(db),
    meetingParticipantsDependencies = participantDependenciesBuilder(db),
    createMeetingRepository = createMeetingRepository(db),
    getMeetingRepository = getMeetingRepository(db),
    participateMeetingRepository = participateMeetingRepository(db),
    deleteMeetingRepository = deleteMeetingRepository(db),
    editMeetingRepository = editMeetingRepository(db)
)
