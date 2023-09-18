package app.meetacy.backend.feature.meetings.usecase.integration.history

import app.meetacy.backend.feature.meetings.usecase.integration.history.active.listMeetingsActiveUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.history.list.listMeetingsHistoryUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.history.past.listMeetingsPastUsecase
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.history() {
    listMeetingsActiveUsecase()
    listMeetingsHistoryUsecase()
    listMeetingsPastUsecase()
}
