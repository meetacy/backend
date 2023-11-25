package app.meetacy.backend.types.search

import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.types.place.Place as PlaceView

sealed interface SearchItem {
    class Meeting(val meeting: MeetingView) : SearchItem
    class User(val user: UserView) : SearchItem
    class Place(val place: PlaceView) : SearchItem
}
