package app.meetacy.backend.database.migrations

import app.meetacy.backend.database.tables
import app.meetacy.database.updater.Wdater
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

private val migrations = listOf(`Migration 0-1`)

suspend fun runMigrations(db: Database) {
    val wdater = Wdater {
        database = db
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
