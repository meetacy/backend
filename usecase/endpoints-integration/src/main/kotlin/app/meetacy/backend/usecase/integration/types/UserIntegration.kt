package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.file.serializable
import app.meetacy.backend.types.serialization.gender.serializable
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.usecase.types.UserView

fun UserView.mapToEndpoint() = User(
    isSelf = isSelf,
    friendship = friendship?.mapToEndpoint(),
    id = identity.serializable(),
    gender = gender?.serializable(),
    nickname = nickname,
    email = email,
    emailVerified = emailVerified,
    avatarIdentity = avatarIdentity?.serializable()
)

fun UserView.Friendship.mapToEndpoint() = when (this) {
    UserView.Friendship.None -> User.Friendship.None
    UserView.Friendship.Subscription -> User.Friendship.Subscription
    UserView.Friendship.Subscriber -> User.Friendship.Subscriber
    UserView.Friendship.Friends -> User.Friendship.Friends
}
