package app.meetacy.backend.integration.test

import app.meetacy.backend.domain.UserId
import app.meetacy.backend.endpoint.friends.get.GetFrendsToken
import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.friends.get.GetFriendsResult
import app.meetacy.backend.endpoint.types.User

object TestGetFriendsRepository : GetFriendsRepository {
    override fun getFriends(token: GetFrendsToken): GetFriendsResult =
        GetFriendsResult.Success(
            friends = listOf(
                User(
                    id = UserId(0),
                    accessHash = "...",
                    nickname = "Emma Watson"
                )
            ),
            subscriptions = listOf(
                User(
                    id = UserId(1),
                    accessHash = "...",
                    nickname = "Elon Musk"
                ),
                User(
                    id = UserId(2),
                    accessHash = "...",
                    nickname = "Timothy Cookothy"
                )
            )
        )
}
