package app.meetacy.backend.application.usecase.meetings

import app.meetacy.backend.application.usecase.meetings.create.createMeetingRepository
import app.meetacy.backend.application.usecase.meetings.delete.deleteMeetingRepository
import app.meetacy.backend.application.usecase.meetings.edit.editMeetingRepository
import app.meetacy.backend.application.usecase.meetings.get.getMeetingRepository
import app.meetacy.backend.application.usecase.meetings.history.meetingsHistoryDependencies
import app.meetacy.backend.application.usecase.meetings.map.listMeetingsMapRepository
import app.meetacy.backend.application.usecase.meetings.participate.participateMeetingRepository
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
