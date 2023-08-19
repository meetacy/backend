package app.meetacy.backend.application.database.meetings.participate

import app.meetacy.feature.meetings.database.integration.meetings.participants.list.DatabaseListMeetingParticipantsStorage
import app.meetacy.feature.meetings.database.integration.meetings.participate.DatabaseParticipateMeetingStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.participateMeetingStorage: ParticipateMeetingUsecase.Storage by Dependency
val DI.listMeetingParticipantsStorage: ListMeetingParticipantsUsecase.Storage by Dependency

fun DIBuilder.participateMeeting() {
    val participateMeetingRepository by singleton<ParticipateMeetingUsecase.Storage> {
        DatabaseParticipateMeetingStorage(database)
    }
    val listMeetingParticipantsStorage by singleton<ListMeetingParticipantsUsecase.Storage> {
        DatabaseListMeetingParticipantsStorage(database)
    }
}
