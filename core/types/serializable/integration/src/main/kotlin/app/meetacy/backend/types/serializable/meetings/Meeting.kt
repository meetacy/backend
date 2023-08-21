package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.location.serializable
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.users.UserView

fun MeetingView.serializable() = Meeting(
    id = identity.serializable(),
    creator = creator.serializable(),
    date = date.serializable(),
    location = location.serializable(),
    title = title,
    description = description,
    participantsCount = participantsCount,
    previewParticipants = previewParticipants.map(UserView::serializable),
    isParticipating = isParticipating,
    avatarIdentity = avatarIdentity?.serializable(),
    visibility = visibility.serializable()
)

fun MeetingView.Visibility.serializable(): Meeting.Visibility = when (this) {
    MeetingView.Visibility.Public -> Meeting.Visibility.Public
    MeetingView.Visibility.Private -> Meeting.Visibility.Private
}

fun Meeting.Visibility.typeFullMeeting(): FullMeeting.Visibility = when (this) {
    Meeting.Visibility.Public -> FullMeeting.Visibility.Public
    Meeting.Visibility.Private -> FullMeeting.Visibility.Private
}
