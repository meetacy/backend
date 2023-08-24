package app.meetacy.backend.feature.meetings.usecase.integration

import app.meetacy.backend.feature.meetings.usecase.integration.create.createMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.delete.deleteMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.edit.editMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.get.getMeetingUsecase
import app.meetacy.backend.feature.meetings.usecase.integration.participate.participateMeetingUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.meetings() {
    createMeetingUsecase()
    deleteMeetingUsecase()
    editMeetingUsecase()
    getMeetingUsecase()
    participateMeetingUsecase()
}
