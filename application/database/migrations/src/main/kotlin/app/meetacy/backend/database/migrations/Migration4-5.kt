package app.meetacy.backend.database.migrations

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsTable
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.MigrationContext

object `Migration4-5` : Migration {
    override val fromVersion = 4

    override suspend fun MigrationContext.migrate() {
        MeetingsTable.DESCRIPTION.modify()
    }
}
