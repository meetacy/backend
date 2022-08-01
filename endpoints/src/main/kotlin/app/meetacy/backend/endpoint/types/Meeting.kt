@file:UseSerializers(
    MeetingIdSerializer::class,
    LocationSerializer::class,
    AccessHashSerializer::class,
    DateSerializer::class
)

package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.Date
import app.meetacy.backend.types.Location
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.serialization.AccessHashSerializer
import app.meetacy.backend.types.serialization.DateSerializer
import app.meetacy.backend.types.serialization.LocationSerializer
import app.meetacy.backend.types.serialization.MeetingIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class Meeting(
    val id: MeetingId,
    val accessHash: AccessHash,
    val creator: User,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val isParticipating: Boolean
)
