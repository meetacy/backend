package app.meetacy.backend.infrastructure.integration.meetings

import app.meetacy.backend.infrastructure.integration.meetings.create.createMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.delete.deleteMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.edit.editMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.get.getMeetingRepository
import app.meetacy.backend.infrastructure.integration.meetings.history.meetingsHistoryDependencies
import app.meetacy.backend.infrastructure.integration.meetings.map.listMeetingsMapRepository
import app.meetacy.backend.infrastructure.integration.meetings.participate.participateMeetingRepository
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
