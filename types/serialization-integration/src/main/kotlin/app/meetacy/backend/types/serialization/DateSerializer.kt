package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.Date
import kotlinx.serialization.Serializer

@Serializer(Date::class)
object DateSerializer
