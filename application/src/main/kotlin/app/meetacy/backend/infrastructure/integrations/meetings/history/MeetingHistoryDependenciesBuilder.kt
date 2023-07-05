package app.meetacy.backend.infrastructure.integrations.meetings.history

import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import org.jetbrains.exposed.sql.Database

fun meetingHistoryDependencies(
    db: Database
): MeetingsHistoryDependencies = MeetingsHistoryDependencies(
    listMeetingsHistoryRepository = listMeetingsHistoryRepository(db),
    meetingsActiveRepository = listActiveMeetingsRepository(db),
    meetingsPastRepository = listPastMeetingsRepository(db),
)
