package app.meetacy.backend.test

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId
import app.meetacy.backend.endpoint.friends.get.GetFriendsToken
import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.friends.get.GetFriendsResult
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.serializable

object TestGetFriendsRepository : GetFriendsRepository {
    override fun getFriends(token: GetFriendsToken): GetFriendsResult =
        GetFriendsResult.Success(
            friends = listOf(
                User(
                    id = UserId(0).serializable(),
                    accessHash = AccessHash("...").serializable(),
                    nickname = "Emma Watson",
                    email = null,
                    emailVerified = null
                )
            ),
            subscriptions = listOf(
                User(
                    id = UserId(1).serializable(),
                    accessHash = AccessHash("...").serializable(),
                    nickname = "Elon Musk",
                    email = null,
                    emailVerified = null
                ),
                User(
                    id = UserId(2).serializable(),
                    accessHash = AccessHash("...").serializable(),
                    nickname = "Timothy Cookothy",
                    email = null,
                    emailVerified = null
                )
            )
        )
}
