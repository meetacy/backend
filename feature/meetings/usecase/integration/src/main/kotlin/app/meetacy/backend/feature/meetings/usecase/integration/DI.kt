package app.meetacy.backend.feature.meetings.usecase.integration

import app.meetacy.backend.feature.meetings.usecase.integration.create.createMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.delete.deleteMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.edit.editMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.get.getMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.get.getMeetingViewsUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.history.history
import app.meetacy.backend.feature.meetings.usecase.integration.map.list.listMeetingsMapUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.participants.list.listMeetingParticipantsUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.participate.participateMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.quit.quitMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.view.viewMeetingsUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.meetings() {
    history()

    createMeetingUsecase()
    deleteMeetingUsecase()
    quitMeetingUsecase()
    editMeetingUsecase()
    getMeetingUsecase()
    getMeetingViewsUsecase()
    listMeetingsMapUsecase()
    listMeetingParticipantsUsecase()
    participateMeetingUsecase()
    viewMeetingsUsecase()
}
