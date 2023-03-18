package app.meetacy.backend.database.types

import app.meetacy.backend.types.PagingId
import app.meetacy.backend.types.UserId

data class DatabaseFriend(
    val pagingId: PagingId,
    val friendId: UserId
)
