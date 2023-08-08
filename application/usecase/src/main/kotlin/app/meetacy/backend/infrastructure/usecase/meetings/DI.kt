package app.meetacy.backend.infrastructure.usecase.meetings

import app.meetacy.backend.infrastructure.usecase.meetings.create.createMeetingRepository
import app.meetacy.backend.infrastructure.usecase.meetings.delete.deleteMeetingRepository
import app.meetacy.backend.infrastructure.usecase.meetings.edit.editMeetingRepository
import app.meetacy.backend.infrastructure.usecase.meetings.get.getMeetingRepository
import app.meetacy.backend.infrastructure.usecase.meetings.history.meetingsHistoryDependencies
import app.meetacy.backend.infrastructure.usecase.meetings.map.listMeetingsMapRepository
import app.meetacy.backend.infrastructure.usecase.meetings.participate.participateMeetingRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.meetings() {
    createMeetingRepository()
    deleteMeetingRepository()
    editMeetingRepository()
    getMeetingRepository()
    meetingsHistoryDependencies()
    listMeetingsMapRepository()
    participateMeetingRepository()
}
