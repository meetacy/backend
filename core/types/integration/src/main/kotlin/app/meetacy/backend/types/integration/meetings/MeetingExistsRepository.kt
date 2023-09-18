package app.meetacy.backend.types.integration.meetings

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.types.meetings.MeetingExistsRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.meetingExistsRepository()  {
    val meetingExistsRepository by singleton {
        val meetingsStorage: MeetingsStorage by getting

        MeetingExistsRepository { identity ->
            meetingsStorage.getMeetingOrNull(identity.id)?.identity == identity
        }
    }
}
