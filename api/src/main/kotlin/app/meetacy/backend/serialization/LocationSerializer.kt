package app.meetacy.backend.serialization

import app.meetacy.backend.domain.Location
import kotlinx.serialization.Serializer

@Serializer(Location::class)
object LocationSerializer
