package app.meetacy.backend.types.serializable.search

import app.meetacy.backend.types.search.SearchItem
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.place.serializable
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.search.SearchItem as SearchItemSerializable

fun SearchItem.serializable() = when (this) {
    is SearchItem.Meeting -> SearchItemSerializable.Meeting(meeting.serializable())
    is SearchItem.Place -> SearchItemSerializable.Place(place.serializable())
    is SearchItem.User -> SearchItemSerializable.User(user.serializable())
}
