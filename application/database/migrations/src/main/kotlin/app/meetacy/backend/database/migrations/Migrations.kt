package app.meetacy.backend.database.migrations

import app.meetacy.backend.database.tables
import app.meetacy.database.updater.Wdater
import app.meetacy.database.updater.log.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

private val migrations = listOf(
    `Migration 0-1`, `Migration 1-2`, `Migration 2-3`,
    `Migration 3-4`, `Migration 4-5`, `Migration 5-6`,
    `Migration 6-7`, `Migration 7-8`, `Migration 8-9`
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
