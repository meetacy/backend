@file:Suppress("ClassName")

package app.meetacy.backend.database.migrations

import app.meetacy.backend.constants.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable
import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable.UPDATED_TIME_MILLIS
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.MigrationContext

object `Migration 3-4` : Migration {
    override val fromVersion = 3

    override suspend fun MigrationContext.migrate() {
        val updatedTimeOld = UsersLocationsTable.varchar("UPDATED_TIME", DATE_TIME_MAX_LIMIT)
        updatedTimeOld.drop()
        UPDATED_TIME_MILLIS.create(System.currentTimeMillis())
    }
}
