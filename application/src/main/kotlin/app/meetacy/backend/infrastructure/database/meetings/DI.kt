@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.meetings

import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.meetingsStorage: MeetingsStorage by Dependency
val DI.participantsStorage: ParticipantsStorage by Dependency

fun DIBuilder.meetings() {
    val meetingsStorage by singleton { MeetingsStorage(database) }
    val participantsStorage by singleton { ParticipantsStorage(database) }
}
