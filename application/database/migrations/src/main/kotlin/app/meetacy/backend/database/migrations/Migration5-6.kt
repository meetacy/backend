package app.meetacy.backend.database.migrations

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsTable
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsTable
import app.meetacy.backend.types.datetime.Date
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.MigrationContext
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object `Migration5-6` : Migration {
    override val fromVersion = 5

    override suspend fun MigrationContext.migrate() {
        MeetingsTable.selectAll().toList().map { meeting ->
            val meetingId = meeting[MeetingsTable.MEETING_ID]
            val date = Date.parseOrNull(meeting[MeetingsTable.DATE])
            if (date != null) return@map
            MeetingsTable.update({ MeetingsTable.MEETING_ID eq meetingId }) { statement ->
                statement[DATE] = Date.yesterday().iso8601
            }
        }

        ParticipantsTable.MEETING_DATE.create(0)

        val dates = ParticipantsTable.selectAll().associate { result ->
            val id = result[ParticipantsTable.MEETING_ID]
            val date = MeetingsTable
                .select { MeetingsTable.MEETING_ID eq id }
                .firstOrNull()
                ?.get(MeetingsTable.DATE)
                ?.let(Date::parse)
            id to date
        }

        ParticipantsTable.selectAll().toList().forEach { row ->
            val meetingId = row[ParticipantsTable.MEETING_ID]
            ParticipantsTable.update({ ParticipantsTable.MEETING_ID eq meetingId }) { statement ->
                statement[MEETING_DATE] = dates[meetingId]?.epochDays ?: 0
            }
        }

    }
}
