package app.meetacy.backend.database.migrations

import app.meetacy.backend.database.tables
import app.meetacy.database.updater.Wdater
import app.meetacy.database.updater.log.Logger
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

private val migrations = listOf(
    `Migration 0-1`, `Migration 1-2`, `Migration 2-3`,
    `Migration3-4`, `Migration4-5`, `Migration5-6`
)

suspend fun runMigrations(db: Database) {
    val wdater = Wdater {
        database = db
        storage = tableStorage(database = db)
        logger = Logger.Simple(includeTimestamp = false)
        initializer {
            createTables(db)
        }
    }
    wdater.update(migrations)
}

private suspend fun createTables(db: Database) {
    newSuspendedTransaction(Dispatchers.IO, db) {
        SchemaUtils.create(*tables.toTypedArray())
    }
}
