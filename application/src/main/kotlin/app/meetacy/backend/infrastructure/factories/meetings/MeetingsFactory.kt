package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.endpoint.meetings.MeetingsDependencies
import app.meetacy.backend.infrastructure.factories.meetings.history.meetingHistoryDependencies
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
