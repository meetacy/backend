package app.meetacy.backend.endpoint.types

import kotlinx.serialization.Serializable

@Serializable
class ServerResponse<T>(val data: T)