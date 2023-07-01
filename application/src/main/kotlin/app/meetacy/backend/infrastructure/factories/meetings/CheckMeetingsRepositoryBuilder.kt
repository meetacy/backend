package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import org.jetbrains.exposed.sql.Database

fun checkMeetingsRepository(db: Database) = DatabaseCheckMeetingsViewRepository(db)
