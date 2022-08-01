package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.UserId
import kotlinx.serialization.Serializer

@Serializer(UserId::class)
object UserIdSerializer
