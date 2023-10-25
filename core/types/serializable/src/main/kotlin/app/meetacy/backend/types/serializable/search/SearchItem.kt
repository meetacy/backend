package app.meetacy.backend.types.serializable.search

import app.meetacy.backend.types.serializable.address.Address
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.meetings.Meeting as MeetingView
import app.meetacy.backend.types.serializable.place.Place as PlaceView
import app.meetacy.backend.types.serializable.users.User as UserView

@Serializable
sealed interface SearchItem {
    @SerialName("meeting")
    @Serializable
    data class Meeting(val meeting: MeetingView) : SearchItem

    @SerialName("user")
    @Serializable
    data class User(val user: UserView) : SearchItem

    @SerialName("place")
    @Serializable
    data class Place(val place: PlaceView) : SearchItem
}
