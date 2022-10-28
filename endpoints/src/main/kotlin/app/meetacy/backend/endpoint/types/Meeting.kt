package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.*
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
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentitySerializable?
)
