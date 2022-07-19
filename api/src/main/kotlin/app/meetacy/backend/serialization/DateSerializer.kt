package app.meetacy.backend.serialization

import app.meetacy.backend.domain.Date
import kotlinx.serialization.Serializer

@Serializer(Date::class)
object DateSerializer
