package app.meetacy.backend.infrastructure.factories.meetings

import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import org.jetbrains.exposed.sql.Database

fun viewMeetingsRepository(db: Database): DatabaseGetMeetingsViewsViewMeetingsRepository =
    DatabaseGetMeetingsViewsViewMeetingsRepository(db)
