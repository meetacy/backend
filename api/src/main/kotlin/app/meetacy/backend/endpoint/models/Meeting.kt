@file:UseSerializers(MeetingIdSerializer::class, LocationSerializer::class)

package app.meetacy.backend.endpoint.models

import app.meetacy.backend.domain.Location
import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.serialization.LocationSerializer
import app.meetacy.backend.serialization.MeetingIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class Meeting(
    val id: MeetingId,
    val accessHash: String,
    val creator: User,
    val date: String,
    val location: Location,
    val title: String?,
    val description: String?
)
