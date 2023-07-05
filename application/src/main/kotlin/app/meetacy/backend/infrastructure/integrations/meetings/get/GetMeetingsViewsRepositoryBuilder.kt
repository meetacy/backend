package app.meetacy.backend.infrastructure.integrations.meetings.get

import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

// TODO: replace with new DI
fun getMeetingsViewsRepository(db: Database): GetMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
