package app.meetacy.backend.endpoint.models

import app.meetacy.backend.domain.Location
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: Long,
    val accessHash: String,
    val creator: User,
    val date: String,
    val location: Location,
    val title: String?,
    val description: String?
)
