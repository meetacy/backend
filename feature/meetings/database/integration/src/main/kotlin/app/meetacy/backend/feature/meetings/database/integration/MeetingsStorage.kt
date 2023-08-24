package app.meetacy.backend.feature.meetings.database.integration

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

internal fun DIBuilder.meetingsStorage() {
    val meetingsStorage by singleton {
        val database: Database by getting
        MeetingsStorage(database)
    }
}
