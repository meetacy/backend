package app.meetacy.backend.types.paging.serializable

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PagingId(val string: String)
