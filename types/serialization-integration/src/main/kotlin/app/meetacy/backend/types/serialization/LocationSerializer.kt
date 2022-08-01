package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.Location
import kotlinx.serialization.Serializer

@Serializer(Location::class)
object LocationSerializer
