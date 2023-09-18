package app.meetacy.backend.feature.meetings.database.integration

import app.meetacy.di.builder.DIBuilder

fun DIBuilder.meetings() {
    meetingsStorage()
    participantsStorage()
}
