package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.DateSerializable
import app.meetacy.backend.types.serialization.LocationSerializable
import app.meetacy.backend.types.serialization.MeetingIdSerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val identity: MeetingIdentitySerializable,
    val creator: User,
    val date: DateSerializable,
    val location: LocationSerializable,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val isParticipating: Boolean
)
