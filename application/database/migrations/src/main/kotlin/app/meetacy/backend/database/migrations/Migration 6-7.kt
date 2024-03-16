@file:Suppress("ClassName")

package app.meetacy.backend.database.migrations

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsTable
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.MigrationContext
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object `Migration 6-7` : Migration {
    override val fromVersion = 6

    override suspend fun MigrationContext.migrate() {
        val oldColumn = ParticipantsTable.long("MEETING_DATE")

        val oldValues = ParticipantsTable
            .slice(oldColumn)
            .selectAll()
            .map { statement -> statement[oldColumn] }
            .iterator()

        ParticipantsTable.MEETING_DATE_EPOCH_DAYS.create(0)

        ParticipantsTable.update { statement ->
            statement[MEETING_DATE_EPOCH_DAYS] = oldValues.next()
        }
    }
}
