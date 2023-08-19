package app.meetacy.backend.application.database.meetings

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.database.meetings.check.checkMeetings
import app.meetacy.backend.application.database.meetings.create.createMeeting
import app.meetacy.backend.application.database.meetings.delete.deleteMeeting
import app.meetacy.backend.application.database.meetings.edit.editMeeting
import app.meetacy.backend.application.database.meetings.get.getMeeting
import app.meetacy.backend.application.database.meetings.history.meetingsHistory
import app.meetacy.backend.application.database.meetings.map.meetingMap
import app.meetacy.backend.application.database.meetings.participate.participateMeeting
import app.meetacy.backend.application.database.meetings.view.viewMeeting
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.meetingsStorage: MeetingsStorage by Dependency
val DI.participantsStorage: ParticipantsStorage by Dependency

fun DIBuilder.meetings() {
    checkMeetings()
    createMeeting()
    deleteMeeting()
    editMeeting()
    getMeeting()
    meetingsHistory()
    meetingMap()
    participateMeeting()
    viewMeeting()
    val meetingsStorage by singleton { MeetingsStorage(database) }
    val participantsStorage by singleton { ParticipantsStorage(database) }
}
