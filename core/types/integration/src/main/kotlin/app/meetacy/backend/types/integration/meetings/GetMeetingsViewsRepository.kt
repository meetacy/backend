package app.meetacy.backend.types.integration.meetings

import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingsViewsUsecase
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getMeetingsViewsRepository() {
    val getMeetingsViewsRepository by singleton {
        val getMeetingsViewsUsecase: GetMeetingsViewsUsecase by getting

        GetMeetingsViewsRepository(
            function = getMeetingsViewsUsecase::getMeetingsViewsOrNull
        )
    }
}
