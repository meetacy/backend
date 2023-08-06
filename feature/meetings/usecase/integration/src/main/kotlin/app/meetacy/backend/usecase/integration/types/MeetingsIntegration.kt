package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.location.serializable
import app.meetacy.backend.types.serializable.meeting.serializable
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.UserView

fun MeetingView.mapToEndpoint() = Meeting(
    id = identity.serializable(),
    creator = creator.mapToEndpoint(),
    date = date.serializable(),
    location = location.serializable(),
    title = title,
    description = description,
    participantsCount = participantsCount,
    previewParticipants = previewParticipants.map(UserView::mapToEndpoint),
    isParticipating = isParticipating,
    avatarIdentity = avatarIdentity?.serializable(),
    visibility = visibility.mapToEndpoint()
)

fun MeetingView.Visibility.mapToEndpoint(): Meeting.Visibility = when (this) {
    MeetingView.Visibility.Public -> Meeting.Visibility.Public
    MeetingView.Visibility.Private -> Meeting.Visibility.Private
}

fun Meeting.Visibility.mapToFullMeeting(): FullMeeting.Visibility = when (this) {
    Meeting.Visibility.Public -> FullMeeting.Visibility.Public
    Meeting.Visibility.Private -> FullMeeting.Visibility.Private
}
