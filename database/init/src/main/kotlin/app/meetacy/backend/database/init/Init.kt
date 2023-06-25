package app.meetacy.backend.database.init

import app.meetacy.backend.database.migrations.runMigrations
import org.jetbrains.exposed.sql.Database

suspend fun initDatabase(db: Database) {
    runMigrations(db)
}
