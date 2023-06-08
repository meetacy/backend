package app.meetacy.backend.database.migrations

import app.meetacy.database.updater.Wdater
import org.jetbrains.exposed.sql.Database

private val migrations = listOf(`Migration 0-1`)

suspend fun runMigrations(db: Database) {
    val wdater = Wdater {
        database = db
    }
    wdater.update(migrations)
}
