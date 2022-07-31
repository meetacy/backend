@file:UseSerializers(
    MeetingIdSerializer::class,
    LocationSerializer::class,
    AccessHashSerializer::class,
    DateSerializer::class
)

package app.meetacy.backend.endpoint.types

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.Location
import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.serialization.AccessHashSerializer
import app.meetacy.backend.serialization.DateSerializer
import app.meetacy.backend.serialization.LocationSerializer
import app.meetacy.backend.serialization.MeetingIdSerializer
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
    val participantsCount: Int
)
