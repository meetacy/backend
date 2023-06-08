@file:Suppress("ClassName")

package app.meetacy.backend.database.migrations

import app.meetacy.backend.database.users.UsersTable
import app.meetacy.database.updater.Migration
import app.meetacy.database.updater.MigrationContext

object `Migration 0-1` : Migration {
    override val fromVersion = 0

    override suspend fun MigrationContext.migrate() {
        UsersTable.USERNAME.create()
    }
}
