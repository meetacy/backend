package app.meetacy.backend.types.integration.meetings

import app.meetacy.backend.feature.meetings.usecase.get.ViewMeetingsUsecase
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.viewMeetingRepository() {
    val viewMeetingsRepository by singleton {
        val viewMeetingsUsecase: ViewMeetingsUsecase by getting

        ViewMeetingsRepository(
            function = viewMeetingsUsecase::viewMeetings
        )
    }
}
