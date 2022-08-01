package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.AccessHash
import kotlinx.serialization.Serializer

@Serializer(AccessHash::class)
object AccessHashSerializer
