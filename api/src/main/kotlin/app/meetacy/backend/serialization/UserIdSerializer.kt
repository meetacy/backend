package app.meetacy.backend.serialization

import app.meetacy.backend.domain.UserId
import kotlinx.serialization.Serializer

@Serializer(UserId::class)
object UserIdSerializer
