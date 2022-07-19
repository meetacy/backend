package app.meetacy.backend.serialization

import app.meetacy.backend.domain.AccessHash
import kotlinx.serialization.Serializer

@Serializer(forClass = AccessHash::class)
object AccessHashSerializer
