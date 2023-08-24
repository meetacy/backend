package app.meetacy.backend.feature.meetings.database.integration

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.participantsStorage() {
    val participantsStorage by singleton {
        val database: Database by getting
        ParticipantsStorage(database)
    }
}
