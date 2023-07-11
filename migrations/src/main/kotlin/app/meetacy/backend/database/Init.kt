package app.meetacy.backend.database

import app.meetacy.backend.database.migrations.runMigrations
import org.jetbrains.exposed.sql.Database

suspend fun initDatabase(db: Database) {
    runMigrations(db)
}
