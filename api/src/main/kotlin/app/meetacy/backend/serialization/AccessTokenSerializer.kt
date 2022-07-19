package app.meetacy.backend.serialization

import app.meetacy.backend.domain.AccessToken
import kotlinx.serialization.Serializer

@Serializer(AccessToken::class)
object AccessTokenSerializer
