package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import org.jetbrains.exposed.sql.Database

fun getMeetingsViewsRepository(db: Database): GetMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
