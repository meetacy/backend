package app.meetacy.backend.types.integration.meetings

import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.meetings() {
    getMeetingsViewsRepository()
    meetingExistsRepository()
    viewMeetingRepository()
}
