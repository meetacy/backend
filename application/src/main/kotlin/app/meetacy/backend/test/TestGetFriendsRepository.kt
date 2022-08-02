package app.meetacy.backend.test

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId
import app.meetacy.backend.endpoint.friends.get.GetFriendsToken
import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.friends.get.GetFriendsResult
import app.meetacy.backend.endpoint.types.User

object TestGetFriendsRepository : GetFriendsRepository {
    override fun getFriends(token: GetFriendsToken): GetFriendsResult =
        GetFriendsResult.Success(
            friends = listOf(
                User(
                    id = UserId(0),
                    accessHash = AccessHash("..."),
                    nickname = "Emma Watson",
                    email = null,
                    emailVerified = null
                )
            ),
            subscriptions = listOf(
                User(
                    id = UserId(1),
                    accessHash = AccessHash("..."),
                    nickname = "Elon Musk",
                    email = null,
                    emailVerified = null
                ),
                User(
                    id = UserId(2),
                    accessHash = AccessHash("..."),
                    nickname = "Timothy Cookothy",
                    email = null,
                    emailVerified = null
                )
            )
        )
}
