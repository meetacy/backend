package app.meetacy.backend.endpoint.types

import kotlinx.serialization.Serializable

@Serializable
class ServerFailure(val errorCode: Int, val errorDescription: String)