package app.meetacy.backend.types.search

import app.meetacy.backend.types.address.Address
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserView

sealed interface SearchResult {
    class Meeting(val meeting: MeetingView) : SearchResult
    class User(val user: UserView) : SearchResult
    class Place(val address: Address) : SearchResult
}
